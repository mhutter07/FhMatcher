package swenga.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import swenga.dao.ProfileDao;
import swenga.dao.UserRoleDao;
import swenga.model.ProfilesModel;
import swenga.model.UserRoleModel;

@Controller
public class SecurityController {
	
	@Autowired
	ProfileDao profileDao;
	
	@Autowired
	UserRoleDao userRoleDao;
	
	
	
	@RequestMapping("/fillUsers")
	@Transactional
	public String fillData(Model model) {
 
		UserRoleModel adminRole = userRoleDao.getRole("ROLE_ADMIN");
		if (adminRole == null)
			adminRole = new UserRoleModel("ROLE_ADMIN");
 
		UserRoleModel userRole = userRoleDao.getRole("ROLE_USER");
		if (userRole == null)
			userRole = new UserRoleModel("ROLE_USER");
		
		Date now = new Date();
		
		if(profileDao.isTableEmpty()) {
		ProfilesModel admin = new ProfilesModel("Admin", "Master", true, now, "admin", "password", true);
		admin.encryptPassword();
		admin.addUserRole(userRole);
		admin.addUserRole(adminRole);
		profileDao.persist(admin);
 
		ProfilesModel user = new ProfilesModel("User", "Test", true, now, "user", "password", true);
		user.encryptPassword();
		user.addUserRole(userRole);
		profileDao.persist(user);
		}
		
		return "forward:login";
	}
	
	
 
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
 
		return "error";
 
	}

}
