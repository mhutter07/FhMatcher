package swenga.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import swenga.dao.AnswerDao;
import swenga.dao.ProfileDao;
import swenga.dao.QuestionDao;
import swenga.dao.UserRoleDao;
import swenga.model.AnswersModel;
import swenga.model.ProfilesModel;
import swenga.model.QuestionModel;
import swenga.model.UserRoleModel;

@Controller
public class SecurityController {
	
	@Autowired
	ProfileDao profileDao;
	
	@Autowired
	UserRoleDao userRoleDao;
	
	@Autowired
	QuestionDao questionDao;
	
	@Autowired
	AnswerDao answerDao;
	
	
	@RequestMapping("/fillUsers")
	@Transactional
	public String fillData(Model model) {
		
		if (questionDao.isTableEmpty()) {
			QuestionModel q1 = new QuestionModel("Interessiert in? [m/w]");
			questionDao.persist(q1);
			QuestionModel q2 = new QuestionModel("Morgen oder Abendmensch? [morgen/abend]");
			questionDao.persist(q2);
			QuestionModel q3 = new QuestionModel("Hunde- oder Katzenmensch? [wau/miau]");
			questionDao.persist(q3);
			QuestionModel q4 = new QuestionModel("Rock- oder Dancemusikliebhaber? [rock/dance]");
			questionDao.persist(q4);
			QuestionModel q5 = new QuestionModel("Stadt- oder Landmensch? [stadt/land]");
			questionDao.persist(q5);
			QuestionModel q6 = new QuestionModel("Unternehmenslustig oder Stubenhocker? [pardey/fernsehn]");
			questionDao.persist(q6);
			QuestionModel q7 = new QuestionModel("Bier- oder Weintrangla? [bier/wein]");
			questionDao.persist(q7);
			QuestionModel q8 = new QuestionModel("Oranges oder Grünes Twinni? [orange/grün]");
			questionDao.persist(q8);
		}
 
		UserRoleModel adminRole = userRoleDao.getRole("ROLE_ADMIN");
		if (adminRole == null)
			adminRole = new UserRoleModel("ROLE_ADMIN");
 
		UserRoleModel userRole = userRoleDao.getRole("ROLE_USER");
		if (userRole == null)
			userRole = new UserRoleModel("ROLE_USER");
		
		Date now = new Date();
		
		if(profileDao.isTableEmpty()) {
		ProfilesModel admin = new ProfilesModel("Admin", "Master", true, now, "admin", "123", true);
		admin.encryptPassword();
		admin.addUserRole(userRole);
		admin.addUserRole(adminRole);
		profileDao.persist(admin);
		
		AnswersModel answer = new AnswersModel(true);
		answer.setProfiles(profileDao.getProfileByUsername("admin"));
		answer.setQuestions(questionDao.getQuestionByID(1));
		answerDao.persist(answer);
		
		AnswersModel answer1 = new AnswersModel(false);
		answer1.setProfiles(profileDao.getProfileByUsername("admin"));
		answer1.setQuestions(questionDao.getQuestionByID(2));
		answerDao.persist(answer1);
		
		AnswersModel answer2 = new AnswersModel(false);
		answer2.setProfiles(profileDao.getProfileByUsername("admin"));
		answer2.setQuestions(questionDao.getQuestionByID(3));
		answerDao.persist(answer2);
		
		AnswersModel answer3 = new AnswersModel(true);
		answer3.setProfiles(profileDao.getProfileByUsername("admin"));
		answer3.setQuestions(questionDao.getQuestionByID(4));
		answerDao.persist(answer3);
		
		AnswersModel answer4 = new AnswersModel(false);
		answer4.setProfiles(profileDao.getProfileByUsername("admin"));
		answer4.setQuestions(questionDao.getQuestionByID(5));
		answerDao.persist(answer4);
		
		AnswersModel answer5 = new AnswersModel(false);
		answer5.setProfiles(profileDao.getProfileByUsername("admin"));
		answer5.setQuestions(questionDao.getQuestionByID(6));
		answerDao.persist(answer5);
		
		AnswersModel answer6 = new AnswersModel(true);
		answer6.setProfiles(profileDao.getProfileByUsername("admin"));
		answer6.setQuestions(questionDao.getQuestionByID(7));
		answerDao.persist(answer6);
		
		AnswersModel answer7 = new AnswersModel(true);
		answer7.setProfiles(profileDao.getProfileByUsername("admin"));
		answer7.setQuestions(questionDao.getQuestionByID(8));
		answerDao.persist(answer7);
		
		ProfilesModel user = new ProfilesModel("Barbara", "Wallner", true, now, "barbara", "123", true);
		user.encryptPassword();
		user.addUserRole(userRole);
		profileDao.persist(user);
		
		AnswersModel answer8 = new AnswersModel(false);
		answer8.setProfiles(profileDao.getProfileByUsername("barbara"));
		answer8.setQuestions(questionDao.getQuestionByID(1));
		answerDao.persist(answer8);
		
		AnswersModel answer9 = new AnswersModel(true);
		answer9.setProfiles(profileDao.getProfileByUsername("barbara"));
		answer9.setQuestions(questionDao.getQuestionByID(2));
		answerDao.persist(answer9);
		
		AnswersModel answer10 = new AnswersModel(false);
		answer10.setProfiles(profileDao.getProfileByUsername("barbara"));
		answer10.setQuestions(questionDao.getQuestionByID(3));
		answerDao.persist(answer10);
		
		AnswersModel answer11 = new AnswersModel(true);
		answer11.setProfiles(profileDao.getProfileByUsername("barbara"));
		answer11.setQuestions(questionDao.getQuestionByID(4));
		answerDao.persist(answer11);
		
		AnswersModel answer12 = new AnswersModel(true);
		answer12.setProfiles(profileDao.getProfileByUsername("barbara"));
		answer12.setQuestions(questionDao.getQuestionByID(5));
		answerDao.persist(answer12);
		
		AnswersModel answer13 = new AnswersModel(false);
		answer13.setProfiles(profileDao.getProfileByUsername("barbara"));
		answer13.setQuestions(questionDao.getQuestionByID(6));
		answerDao.persist(answer13);
		
		AnswersModel answer14 = new AnswersModel(false);
		answer14.setProfiles(profileDao.getProfileByUsername("barbara"));
		answer14.setQuestions(questionDao.getQuestionByID(7));
		answerDao.persist(answer14);
		
		AnswersModel answer15 = new AnswersModel(false);
		answer15.setProfiles(profileDao.getProfileByUsername("barbara"));
		answer15.setQuestions(questionDao.getQuestionByID(8));
		answerDao.persist(answer15);
		
		ProfilesModel user1 = new ProfilesModel("Franz", "Wachter", false, now, "franz", "123", true);
		user1.encryptPassword();
		user1.addUserRole(userRole);
		profileDao.persist(user1);
		
		AnswersModel answer16 = new AnswersModel(false);
		answer16.setProfiles(profileDao.getProfileByUsername("franz"));
		answer16.setQuestions(questionDao.getQuestionByID(1));
		answerDao.persist(answer16);
		
		AnswersModel answer17 = new AnswersModel(true);
		answer17.setProfiles(profileDao.getProfileByUsername("franz"));
		answer17.setQuestions(questionDao.getQuestionByID(2));
		answerDao.persist(answer17);
		
		AnswersModel answer18 = new AnswersModel(false);
		answer18.setProfiles(profileDao.getProfileByUsername("franz"));
		answer18.setQuestions(questionDao.getQuestionByID(3));
		answerDao.persist(answer18);
		
		AnswersModel answer19 = new AnswersModel(true);
		answer19.setProfiles(profileDao.getProfileByUsername("franz"));
		answer19.setQuestions(questionDao.getQuestionByID(4));
		answerDao.persist(answer19);
		
		AnswersModel answer20 = new AnswersModel(true);
		answer20.setProfiles(profileDao.getProfileByUsername("franz"));
		answer20.setQuestions(questionDao.getQuestionByID(5));
		answerDao.persist(answer20);
		
		AnswersModel answer21 = new AnswersModel(false);
		answer21.setProfiles(profileDao.getProfileByUsername("franz"));
		answer21.setQuestions(questionDao.getQuestionByID(6));
		answerDao.persist(answer21);
		
		AnswersModel answer22 = new AnswersModel(false);
		answer22.setProfiles(profileDao.getProfileByUsername("franz"));
		answer22.setQuestions(questionDao.getQuestionByID(7));
		answerDao.persist(answer22);
		
		AnswersModel answer23 = new AnswersModel(false);
		answer23.setProfiles(profileDao.getProfileByUsername("franz"));
		answer23.setQuestions(questionDao.getQuestionByID(8));
		answerDao.persist(answer23);
		
		ProfilesModel user2 = new ProfilesModel("Martin", "Hutter", false, now, "martin", "123", true);
		user2.encryptPassword();
		user2.addUserRole(userRole);
		profileDao.persist(user2);
		
		AnswersModel answer24 = new AnswersModel(false);
		answer24.setProfiles(profileDao.getProfileByUsername("martin"));
		answer24.setQuestions(questionDao.getQuestionByID(1));
		answerDao.persist(answer24);
		
		AnswersModel answer25 = new AnswersModel(true);
		answer25.setProfiles(profileDao.getProfileByUsername("martin"));
		answer25.setQuestions(questionDao.getQuestionByID(2));
		answerDao.persist(answer25);
		
		AnswersModel answer26 = new AnswersModel(false);
		answer26.setProfiles(profileDao.getProfileByUsername("martin"));
		answer26.setQuestions(questionDao.getQuestionByID(3));
		answerDao.persist(answer26);
		
		AnswersModel answer27 = new AnswersModel(true);
		answer27.setProfiles(profileDao.getProfileByUsername("martin"));
		answer27.setQuestions(questionDao.getQuestionByID(4));
		answerDao.persist(answer27);
		
		AnswersModel answer28 = new AnswersModel(true);
		answer28.setProfiles(profileDao.getProfileByUsername("martin"));
		answer28.setQuestions(questionDao.getQuestionByID(5));
		answerDao.persist(answer28);
		
		AnswersModel answer29 = new AnswersModel(false);
		answer29.setProfiles(profileDao.getProfileByUsername("martin"));
		answer29.setQuestions(questionDao.getQuestionByID(6));
		answerDao.persist(answer29);
		
		AnswersModel answer30 = new AnswersModel(false);
		answer30.setProfiles(profileDao.getProfileByUsername("martin"));
		answer30.setQuestions(questionDao.getQuestionByID(7));
		answerDao.persist(answer30);
		
		AnswersModel answer31 = new AnswersModel(false);
		answer31.setProfiles(profileDao.getProfileByUsername("martin"));
		answer31.setQuestions(questionDao.getQuestionByID(8));
		answerDao.persist(answer31);
		
		ProfilesModel user4 = new ProfilesModel("Dominik", "Pagger", false, now, "dominik", "123", true);
		user4.encryptPassword();
		user4.addUserRole(userRole);
		profileDao.persist(user4);
		
		AnswersModel answer32 = new AnswersModel(false);
		answer32.setProfiles(profileDao.getProfileByUsername("franz"));
		answer32.setQuestions(questionDao.getQuestionByID(1));
		answerDao.persist(answer32);
		
		AnswersModel answer33 = new AnswersModel(true);
		answer33.setProfiles(profileDao.getProfileByUsername("franz"));
		answer33.setQuestions(questionDao.getQuestionByID(2));
		answerDao.persist(answer33);
		
		AnswersModel answer34 = new AnswersModel(false);
		answer34.setProfiles(profileDao.getProfileByUsername("franz"));
		answer34.setQuestions(questionDao.getQuestionByID(3));
		answerDao.persist(answer34);
		
		AnswersModel answer35 = new AnswersModel(true);
		answer35.setProfiles(profileDao.getProfileByUsername("franz"));
		answer35.setQuestions(questionDao.getQuestionByID(4));
		answerDao.persist(answer35);
		
		AnswersModel answer36 = new AnswersModel(true);
		answer36.setProfiles(profileDao.getProfileByUsername("franz"));
		answer36.setQuestions(questionDao.getQuestionByID(5));
		answerDao.persist(answer36);
		
		AnswersModel answer37 = new AnswersModel(false);
		answer37.setProfiles(profileDao.getProfileByUsername("franz"));
		answer37.setQuestions(questionDao.getQuestionByID(6));
		answerDao.persist(answer37);
		
		AnswersModel answer38 = new AnswersModel(false);
		answer38.setProfiles(profileDao.getProfileByUsername("franz"));
		answer38.setQuestions(questionDao.getQuestionByID(7));
		answerDao.persist(answer38);
		
		AnswersModel answer39 = new AnswersModel(false);
		answer39.setProfiles(profileDao.getProfileByUsername("franz"));
		answer39.setQuestions(questionDao.getQuestionByID(8));
		answerDao.persist(answer39);
		
		ProfilesModel user5 = new ProfilesModel("User", "Default", false, now, "user", "123", true);
		user5.encryptPassword();
		user5.addUserRole(userRole);
		profileDao.persist(user5);
		
		AnswersModel answer40 = new AnswersModel(false);
		answer40.setProfiles(profileDao.getProfileByUsername("franz"));
		answer40.setQuestions(questionDao.getQuestionByID(1));
		answerDao.persist(answer40);
		
		AnswersModel answer41 = new AnswersModel(true);
		answer41.setProfiles(profileDao.getProfileByUsername("franz"));
		answer41.setQuestions(questionDao.getQuestionByID(2));
		answerDao.persist(answer41);
		
		AnswersModel answer42 = new AnswersModel(false);
		answer42.setProfiles(profileDao.getProfileByUsername("franz"));
		answer42.setQuestions(questionDao.getQuestionByID(3));
		answerDao.persist(answer42);
		
		AnswersModel answer43 = new AnswersModel(true);
		answer43.setProfiles(profileDao.getProfileByUsername("franz"));
		answer43.setQuestions(questionDao.getQuestionByID(4));
		answerDao.persist(answer43);
		
		AnswersModel answer44 = new AnswersModel(true);
		answer44.setProfiles(profileDao.getProfileByUsername("franz"));
		answer44.setQuestions(questionDao.getQuestionByID(5));
		answerDao.persist(answer44);
		
		AnswersModel answer45 = new AnswersModel(false);
		answer45.setProfiles(profileDao.getProfileByUsername("franz"));
		answer45.setQuestions(questionDao.getQuestionByID(6));
		answerDao.persist(answer45);
		
		AnswersModel answer46 = new AnswersModel(false);
		answer46.setProfiles(profileDao.getProfileByUsername("franz"));
		answer46.setQuestions(questionDao.getQuestionByID(7));
		answerDao.persist(answer46);
		
		AnswersModel answer47 = new AnswersModel(false);
		answer47.setProfiles(profileDao.getProfileByUsername("franz"));
		answer47.setQuestions(questionDao.getQuestionByID(8));
		answerDao.persist(answer47);
		
		}
		
		return "forward:login";
	}
	
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {
 
		return "error";
 
	}

}
