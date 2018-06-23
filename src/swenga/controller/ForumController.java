package swenga.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import swenga.dao.PostDao;
import swenga.model.PostModel;
import swenga.model.ProfilesModel;

@Controller
public class ForumController {
	
	@Autowired
	PostDao postDao;

	public String getUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}
	
	@RequestMapping(value = "/forum", method = RequestMethod.GET)
	public String goToForum() {
		return "forward:/forumFill";
	}
	
	@RequestMapping(value = "/forumFill", method = RequestMethod.GET)
	public String ForumFill(Model model) {
		List<PostModel> posts = postDao.getPosts();
		model.addAttribute("posts", posts);
		return "forum";
	}
	
	@RequestMapping(value = "/forum/check", method = RequestMethod.GET)
	public String checkPost(Model model, @RequestParam int id) {
		PostModel post = postDao.getById(id);
		model.addAttribute("post",post);
		return "check";
	}
	
	@RequestMapping(value = "/forum/post", method = RequestMethod.GET)
	public String startPost() {
		return "post";
	}
	
	@RequestMapping(value = "/forum/post$confirm", method = RequestMethod.POST)
	public String sendPost(@Valid PostModel newForumModel, Model model, @RequestParam("header") String header, @RequestParam("post") String text) {
		if(header.isEmpty() || text.isEmpty()) {
			model.addAttribute("errorMessage", "Please fill all Fields!");
			return "post";
		}
		PostModel post = new PostModel(getUsername(), header, text);
		postDao.persist(post);
		return "redirect:/forum";
	}
	
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "error";
	}
	
}
