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
		
		QuestionModel q1 = new QuestionModel("Interessiert in?");
		questionDao.persist(q1);
		QuestionModel q2 = new QuestionModel("Gesuchtes Alter?");
		questionDao.persist(q2);		
		QuestionModel q3 = new QuestionModel("Was finden Sie in einer Beziehung am Wichtigsten?");
		questionDao.persist(q3);
		QuestionModel q4 = new QuestionModel("Was ist Ihnen an dem Partner am Wichtigsten?");
		questionDao.persist(q4);		
		QuestionModel q5 = new QuestionModel("Oranges oder Grünes Twinni?");
		questionDao.persist(q5);


		return "forward:question";
	}
	
	
	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "error";

	}

}
