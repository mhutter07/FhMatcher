package swenga.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@RequestMapping(value = { "list" })
	public String index(Model model) {

		List<ProfilesModel> profiles = profileDao.getProfiles();

		model.addAttribute("profiles", profiles);
		return "index";
	}
	
	@RequestMapping(value = { "/fillMembers" })
	public String fillMembers(Model model) {

		List<ProfilesModel> profiles = profileDao.getProfiles();

		model.addAttribute("profiles", profiles);
		return "admin";
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

		return "forward:fillMembers";
	}
	
	@RequestMapping("/searchProfiles")
	public String search(Model model, @RequestParam String searchString) {
		model.addAttribute("profiles", profileDao.searchProfiles(searchString));

		return "admin";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/delete")
	public String deleteData(Model model, @RequestParam int id) {
		profileDao.delete(id);

		return "forward:fillMembers";
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profileCall() {
		return "profile";
	}
	
	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
	public String handleLogin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getName().equals("anonymousUser"))
		return "login";
		else return "profile";
	}
	
	@RequestMapping(value = "/addProfile", method = RequestMethod.GET)
	public String addProfile() {
		return "addProfile";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin() {
		return "admin";
	}
	

	@RequestMapping(value = "/addProfile", method = RequestMethod.POST)
	public String addProfile(@Valid ProfilesModel newProfilesModel, BindingResult bindingResult, Model model, 
			@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname, @RequestParam("gender") String gender,
			@RequestParam("dayOfBirth") String dayOfBirth, @RequestParam("username") String username, 
			@RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword) throws ParseException, java.text.ParseException {
		
		if (bindingResult.hasErrors()) {
			String errorMessage = "";
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errorMessage += fieldError.getField() + " is invalid<br>";
			}
			model.addAttribute("errorMessage", errorMessage);
			
			return "forward:login";
		}
		
		List<ProfilesModel> user = profileDao.findByUsername(username);
		
		if (CollectionUtils.isEmpty(user)) {
			
			if (firstname.isEmpty()) {
				model.addAttribute("errorMessage", "Please enter a valid first name!");
				return "/addProfile";
			}
			if (lastname.isEmpty()) {
				model.addAttribute("errorMessage", "Please enter a valid last name!");
				return "/addProfile";
			}
			if (gender.isEmpty()) {
				model.addAttribute("errorMessage", "Please enter a valid gender!");
				return "/addProfile";
			}
			
			if (dayOfBirth.isEmpty()) {
				model.addAttribute("errorMessage", "Please enter a valid day of birth!");
				return "/addProfile";
			}
			
			if (username.isEmpty()) {
				model.addAttribute("errorMessage", "Please enter a valid user name!");
				return "/addProfile";
			}
			
			if (password.isEmpty()) {
				model.addAttribute("errorMessage", "Please enter a valid password!");
				return "/addProfile";
			}
			
			if (confirmPassword.isEmpty()) {
				model.addAttribute("errorMessage", "Please confirm your password!");
				return "/addProfile";
			}
			
			if (!(confirmPassword.equals(password))) {
				model.addAttribute("errorMessage", "Your passwords did not match!" + confirmPassword + password);
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
