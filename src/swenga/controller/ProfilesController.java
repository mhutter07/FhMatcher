package swenga.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import swenga.dao.ProfileDao;
import swenga.dao.UserRoleDao;
import swenga.model.ProfilesModel;
import swenga.model.UserRoleModel;

@Controller
public class ProfilesController {
	
	@Autowired
	ProfileDao profileDao;
	
	@Autowired
	UserRoleDao userRoleDao;
	
	@RequestMapping(value = { "/", "list" })
	public String index(Model model) {

		List<ProfilesModel> profiles = profileDao.getProfiles();

		model.addAttribute("profiles", profiles);
		return "index";
	}
	
	@RequestMapping("/fillData")
	@Transactional
	public String fillData(Model model) {

		Date now = new Date();
		
		UserRoleModel adminRole = userRoleDao.getRole("ROLE_ADMIN");
		if (adminRole == null)
			adminRole = new UserRoleModel("ROLE_ADMIN");
 
		UserRoleModel userRole = userRoleDao.getRole("ROLE_USER");
		if (userRole == null)
			userRole = new UserRoleModel("ROLE_USER");

		ProfilesModel user1 = new ProfilesModel("Dominik", "Pagger", false, now, "domi", "password", true);
		user1.encryptPassword();
		user1.addUserRole(userRole);
		profileDao.persist(user1);

		ProfilesModel user2 = new ProfilesModel("Miriam", "Grainer", true, now, "miri", "password", true);
		user2.encryptPassword();
		user2.addUserRole(userRole);
		profileDao.persist(user2);

		return "forward:list";
	}
	
	@RequestMapping("/searchProfiles")
	public String search(Model model, @RequestParam String searchString) {
		model.addAttribute("profiles", profileDao.searchProfiles(searchString));

		return "index";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/delete")
	public String deleteData(Model model, @RequestParam int id) {
		profileDao.delete(id);

		return "forward:list";
	}
	
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profileCall() {
		return "profile";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String handleLogin() {
		return "login";
	}
	
	@RequestMapping(value = "/addProfile", method = RequestMethod.GET)
	public String addProfile() {
		return "addProfile";
	}


	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin() {
		return "admin";
	}
	
/*
	@RequestMapping(value = "/addProfile", method = RequestMethod.POST)
	public String addProfile(@Valid ProfilesModel newProfilesModel, BindingResult bindingResult, Model model, 
			@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname, @RequestParam("gender") String gender,
			@RequestParam("dayOfBirth") String dayOfBirth, @RequestParam("username") String username, 
			@RequestParam("password") String password) throws ParseException, java.text.ParseException {
		
		if (bindingResult.hasErrors()) {
			String errorMessage = "";
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errorMessage += fieldError.getField() + " is invalid<br>";
			}
			model.addAttribute("errorMessage", errorMessage);
			
			return "forward:list";
		}
		
		List<ProfilesModel> user = profileDao.findByUsername(username);
		
		if (CollectionUtils.isEmpty(user)) {
			
			if (dayOfBirth.isEmpty()) {
				model.addAttribute("errorMessage", "Please enter a valid day of birth!");
				return "/addProfile";
			}
			else {
			
			SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
			Date birthday = formatDate.parse(dayOfBirth);
			
			UserRoleModel role = userRoleDao.getRole("ROLE_USER");
					if (role == null) {
						role = new UserRoleModel("ROLE_USER");
					}
					
			ProfilesModel newUser = new ProfilesModel(firstname, lastname, Boolean.valueOf(gender), birthday, username, password, true);
			newUser.encryptPassword();
			newUser.addUserRole(role);
			profileDao.merge(newUser);
			
			
			return "forward:list";
			}
			
		}
		
		else {

			model.addAttribute("errorMessage", "User name is already taken!");
			return "/addProfile";
		}
		
		
	}
	

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "error";

	}
	


}
