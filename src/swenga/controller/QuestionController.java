package swenga.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import swenga.dao.QuestionDao;
import swenga.model.QuestionModel;

@Controller
public class QuestionController {
	
	@Autowired
	QuestionDao questionDao;	
	
<<<<<<< HEAD
	@RequestMapping(value = "/question", method = RequestMethod.GET)
	public String callQuestion() {
		return "/question";
=======
	@RequestMapping(value = { "/", "listQuestion" })
	public String questionascv(Model model) {

		List<QuestionModel> questions = questionDao.getQuestions();

		model.addAttribute("questions", questions);
		return "question";
>>>>>>> controller fix test
	}
	
	
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "error";

	}

}
