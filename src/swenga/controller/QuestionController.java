package swenga.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import swenga.dao.QuestionDao;
import swenga.model.ProfilesModel;
import swenga.model.QuestionModel;

@Controller
public class QuestionController {
	
	@Autowired
	QuestionDao questionDao;	
	
	@RequestMapping(value = "/question", method = RequestMethod.GET)
	public String callQuestion(Model model) {
		
		List<QuestionModel> questions = questionDao.getQuestions();

		model.addAttribute("questions", questions);
		
		return "/question";
	}
	
	@RequestMapping("/fillQuestions")
	@Transactional
	public String fillQuestions(Model model) {
		
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
		
		return "forward:question";
	}
	
	
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "error";

	}

}
