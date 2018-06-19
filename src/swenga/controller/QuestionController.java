package swenga.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import swenga.dao.QuestionDao;
import swenga.model.QuestionModel;

@Controller
public class QuestionController {
	
	@Autowired
	QuestionDao questionDao;
	
	@RequestMapping(value = { "/", "listQuestion" })
	public String index(Model model) {

		List<QuestionModel> questions = questionDao.getQuestions();

		model.addAttribute("questions", questions);
		return "question";
	}
	
	@RequestMapping("/fillQuestion")
	@Transactional
	public String fillQuestion(Model model) {


		QuestionModel q1 = new QuestionModel("Frage 1");
		questionDao.persist(q1);

		QuestionModel q2 = new QuestionModel("Frage 2");
		questionDao.persist(q2);

		QuestionModel q3 = new QuestionModel("Frage 3");
		questionDao.persist(q3);

		return "forward:listQuestion";
	}
	
	@RequestMapping(value = "/question", method = RequestMethod.GET)
	public String callQuestion() {
		return "/question";
	}

}
