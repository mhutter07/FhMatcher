package swenga.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.List;

import java.util.Optional;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;



import swenga.dao.ProfileDao;
import swenga.dao.UserRoleDao;
import swenga.model.ProfilesModel;
import swenga.model.UserRoleModel;
import swenga.jpa.dao.DocumentRepo;
import swenga.model.DocumentModel;
import swenga.jpa.dao.ProfileRepo;
import swenga.model.QuestionModel;

@Controller
public class ProfilesController {
	
	@Autowired
	ProfileDao profileDao;
	
	@Autowired
	UserRoleDao userRoleDao;
	
	@Autowired
	DocumentRepo documentRepository;
	
	@Autowired
	ProfileRepo profileRepository;
	
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

		if(profileDao.isTableEmpty()) {
		ProfilesModel user1 = new ProfilesModel("Dominik", "Pagger", false, now, "domi", "password", true);
		user1.encryptPassword();
		user1.addUserRole(userRole);
		profileDao.persist(user1);

		ProfilesModel user2 = new ProfilesModel("Miriam", "Grainer", true, now, "miri", "password", true);
		user2.encryptPassword();
		user2.addUserRole(userRole);
		profileDao.persist(user2);
		}
		return "forward:fillMembers";
	}
	
	@RequestMapping("/searchProfiles")
	public String search(Model model, @RequestParam String searchString) {
		model.addAttribute("profiles", profileDao.searchProfiles(searchString));

		return "admin";
	}
	
	/*List<ProfilesModel> profiles = profileDao.getProfiles();
	model.addAttribute("profiles", profiles);*/
	
	public String getUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}
	
	@RequestMapping(value = "/profile/{name}", method = RequestMethod.GET)
	public String profileCall(Model model, @PathVariable("name") String name) {
		List<ProfilesModel> profi = profileDao.requestProfile(name);		
		if(!profi.isEmpty()) {
			model.addAttribute("profi",profi);
			return "profile";
		}
		else {
			model.addAttribute("errorMessage","Profile does not exist!");
			return "forward:" + getUsername();
		}
	}
	
	@RequestMapping(value= "/profileForward", method = RequestMethod.GET)
	public String profileForward(Model model, @RequestParam("username") String name) {
		List<ProfilesModel> profi = profileDao.requestProfile(name);		
		if(!profi.isEmpty()) {
			model.addAttribute("profi",profi);
			return "profile";
		}
		else {
			model.addAttribute("errorMessage","Profile does not exist!");
			return "matches";
		}
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping("/delete")
	public String deleteData(Model model, @RequestParam int id) {
		profileDao.delete(id);

		return "forward:fillMembers";
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profileCallSelf(Model model) {
		
		List<ProfilesModel> profi = profileDao.requestProfile(getUsername());
		model.addAttribute("profi",profi);
		
		return "profile";
	}
	
	
	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
	public String handleLogin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth.getName().equals("anonymousUser"))
		return "login";
		else return "forward:profile/" + getUsername();
	}
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin() {
		return "forward:fillMembers";
	}
<<<<<<< HEAD
=======
	
	@RequestMapping(value = "/matches", method = RequestMethod.GET)
	public String matches(Model model) {
		return "matches";
	}	
>>>>>>> Master/master

	@RequestMapping(value = "/addProfile", method = RequestMethod.GET)
	public String addProfile() {
		return "addProfile";
	}
	
	@RequestMapping(value = "/addProfile", method = RequestMethod.POST)
	public String addProfile(@Valid ProfilesModel newProfilesModel, BindingResult bindingResult, Model model, 
			@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname, @RequestParam("gender") String gender,
			@RequestParam("dayOfBirth") String dayOfBirth, @RequestParam("username") String username, 
			@RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword) throws ParseException, java.text.ParseException {
		
		Date now = new Date();
		SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
		Date birthday = formatDate.parse(dayOfBirth);
		
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
			
			if (dayOfBirth.isEmpty() || birthday.compareTo(now) > 0) {
				model.addAttribute("errorMessage", "Please enter a valid day of birth!");
				return "/addProfile";
			}
			
			if (username.isEmpty()) {
				model.addAttribute("errorMessage", "Please enter a valid user name!");
				return "/addProfile";
			}
			
			if (username.equals("anonymousUser")) {
				model.addAttribute("errorMessage", "The username is reserved!");
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
				model.addAttribute("errorMessage", "Your passwords did not match!");
				return "/addProfile";
			}
			
			else {
							
				UserRoleModel role = userRoleDao.getRole("ROLE_USER");
						if (role == null) {
							role = new UserRoleModel("ROLE_USER");
						}
						
				ProfilesModel newUser = new ProfilesModel(firstname, lastname, Boolean.valueOf(gender), birthday, username, password, true);
				newUser.encryptPassword();
				newUser.addUserRole(role);
				profileDao.merge(newUser);				
				
				return "forward:/list";
			}
		}
		else {

			model.addAttribute("errorMessage", "User name is already taken!");
			return "/addProfile";
		}
		
		
	}
	
	@RequestMapping(value = "/editProfile", method = RequestMethod.GET)
	public String editProfile() {
		return "editProfile";
	}
	
	@RequestMapping(value = "/editProfile", method = RequestMethod.POST)
	public String editProfile(@Valid ProfilesModel newProfileModel, BindingResult bindingResult, 
			Model model, @RequestParam("username") String username, @RequestParam("oldPassword") String oldPassword, 
			@RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword) {
		
		if (bindingResult.hasErrors()) {
			String errorMessage = "";
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				errorMessage += fieldError.getField() + " is invalid<br>";
			}
			model.addAttribute("errorMessage", errorMessage);
			return "forward:/listEmployees";
		}
				
		if (username.isEmpty()) {
			model.addAttribute("errorMessage", "Please enter a valid username!");
			return "editProfile";
		}
		if (username.equals("anonymousUser")) {
			model.addAttribute("errorMessage", "The username is reserved!");
			return "editProfile";
		}
		if(oldPassword.isEmpty()) {
			model.addAttribute("errorMessage", "Please enter your old password!");
			return "editProfile";
		}
		if(newPassword.isEmpty()) {
			newPassword = oldPassword;
		}
		if(confirmPassword.isEmpty()) {
			confirmPassword = oldPassword;
		}
		else {
			List<ProfilesModel> user = profileDao.findByUsername(username);
			
			if (!CollectionUtils.isEmpty(user)) {
					
				model.addAttribute("errorMessage", "User name is already taken!");
				return "editProfile";
			}
			else {
				
				String currentName = getUsername();
				List<ProfilesModel> profile = profileDao.findByUsername(currentName);
				System.out.println("1: " + currentName);
				
				ProfilesModel changingProfile = profileDao.getProfiles(profile.get(0).getId());
				System.out.println("2: " + changingProfile.getPassword());
				System.out.println(oldPassword);
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				
				if (!encoder.matches(oldPassword, changingProfile.getPassword())) {
					model.addAttribute("errorMessage", "Your old password was incorrect!");
					return "editProfile";
				}
				
				if (!newPassword.equals(confirmPassword)) {
					model.addAttribute("errorMessage", "Your passwords did not match!");
					return "editProfile";
				}
				else {
					changingProfile.setUserName(username);
					changingProfile.setPassword(newPassword);
					changingProfile.encryptPassword();
					profileDao.merge(changingProfile);
					
					return "redirect:/logout";
				}
			}
		}
		return "forward:editProfile";
	}
	
	@RequestMapping(value = "/block", method = RequestMethod.GET)
	public String blockUser(Model model, int id) {
		
		ProfilesModel bannedProfile = profileDao.getProfiles(id);
		
		bannedProfile.setEnabled(!bannedProfile.isEnabled());
		profileDao.merge(bannedProfile); 
		
		return "forward:fillMembers";
	}
	
	

	/**
	 * Display the upload form
	 * @param model
	 * @param profileId
	 * @return
	 */
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String showUploadForm(Model model, @RequestParam ("id") int profileId) {
		String user = getUsername();
		model.addAttribute("profileId", profileId);
		model.addAttribute("user", user);
		return "uploadFile";
	}
	
	
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadDocument(Model model, @RequestParam("id") int profileId,
			@RequestParam("myFile") MultipartFile file) {
		
		try {
			
			Optional<ProfilesModel> profileOpt = profileRepository.findById(profileId);
			if (!profileOpt.isPresent()) throw new IllegalArgumentException("No user with id"+profileId);
			
			ProfilesModel profile = profileOpt.get();
			
			//Already a document available -> delete it
			/*if (profile.getDocument() != null) {
				documentRepository.delete(profile.getDocument());
				//remove relationship
				profile.setDocument(null);
			}*/
			
			//Create new Document with all infos
			DocumentModel document = new DocumentModel();
			document.setContent(file.getBytes());
			document.setContentType(file.getContentType());
			//document.setCreated(new Date());
			document.setFilename(file.getOriginalFilename());
			document.setName(file.getOriginalFilename());
			document.setProfiles(profile);
			profile.addDocument(document);
			documentRepository.save(document);
			model.addAttribute("message","Bild wurde hochgeladen!");
		} catch (Exception ex) {
			model.addAttribute("errorMessage","Error:" + ex.getMessage());
		}
		
			//model.addAttribute("profiles", profiles);
		
		return "redirect:/profile/"+getUsername();
	}

/*
	@RequestMapping("profile/imageUp")
	public void download(@RequestParam("id") int documentId, HttpServletResponse response) {


	
	@RequestMapping("/profile/imageUp")
	public void download(@RequestParam("documentId") int documentId, HttpServletResponse response) {

		
		Optional<DocumentModel> docOpt = documentRepository.findById(documentId);
		if (!docOpt.isPresent()) throw new IllegalArgumentException("No document with id "+documentId);
		
		DocumentModel doc = docOpt.get();
		
		
		try {
			//response.setHeader("Content-Disposition", "inline;filename=\"" + doc.getFilename() + "\"");
			OutputStream out = response.getOutputStream();	
			response.setContentType(doc.getContentType());
			out.write(doc.getContent());
			out.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	

	@RequestMapping("/profile/image")
	public void downloadImage(@RequestParam("id") int profileId, HttpServletResponse response) {
		
		Optional<ProfilesModel> profileOpt = profileRepository.findById(profileId);
		if (!profileOpt.isPresent())
			throw new IllegalArgumentException("No profile with id " + profileId);
		
		ProfilesModel profile = profileOpt.get();
		
		List<DocumentModel> docOpt = documentRepository.findAllByName(profile);
		DocumentModel doc = docOpt.get(0);
		
		try {
			
			OutputStream out = response.getOutputStream();
			response.setContentType(doc.getContentType());
			out.write(doc.getContent());
			out.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	

	
	// nach klick auf "Upload" Button , Verweis auf die Seite -> http://localhost:8080/FhMatcher/upload?id=46
	
	
	@RequestMapping(value = "/addQuestions", method = RequestMethod.POST)
	public String addQuestions(@Valid ProfilesModel newProfilesModel, BindingResult bindingResult, Model model, 
			@RequestParam("question") QuestionModel questionID) {
	
			System.out.println(questionID);
			
			return "fillQuestions";

	}

	/*@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "error";
	}*/
	
}


