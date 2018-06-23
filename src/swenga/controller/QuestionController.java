package swenga.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import swenga.dao.AnswerDao;
import swenga.dao.ProfileDao;
import swenga.dao.QuestionDao;
import swenga.model.AnswersModel;
import swenga.model.ProfilesModel;
import swenga.model.QuestionModel;

@Controller
public class QuestionController {

	@Autowired
	QuestionDao questionDao;

	@Autowired
	AnswerDao answerDao;

	@Autowired
	ProfileDao profileDao;

	public String getUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}

	@RequestMapping(value = "/questionNext{questionVal}", method = { RequestMethod.GET, RequestMethod.POST })
	public String question(Model model, @PathVariable("questionVal") int questionVal) {
		int questionAtm = questionVal + 1;

		if (questionAtm == 9) {
			return "redirect:/profile/" + getUsername();
		} else {
			QuestionModel question = questionDao.getQuestionByID(questionAtm);
			model.addAttribute("question", question);
			model.addAttribute("questionAtm", questionAtm);

			return "question";
		}
	}

	@RequestMapping(value = "/question", method = { RequestMethod.GET, RequestMethod.POST })
	public String question(Model model) {
		int questionAtm = 1;
		QuestionModel question = questionDao.getQuestionByID(questionAtm);
		model.addAttribute("question", question);
		model.addAttribute("questionAtm", questionAtm);
		return "question";
	}

	@RequestMapping("/fillAnswers")
	public String fillAnswers(Model model, @RequestParam("answer") String answer,
			@RequestParam("questionVal") int questionID) {
		AnswersModel a1 = new AnswersModel(Boolean.valueOf(answer));
		a1.setProfiles(profileDao.getProfileByUsername(getUsername()));
		a1.setQuestions(questionDao.getQuestionByID(questionID));
		answerDao.merge(a1);
		return "forward:/questionNext" + questionID;
	}

	@RequestMapping("/dropAnswers")
	public String dropAnswers() {
		Set<AnswersModel> answers = profileDao.getProfileByUsername(getUsername()).getAnswers();
		profileDao.getProfileByUsername(getUsername()).setAnswers(null);
		for (AnswersModel answer : answers) {
			answerDao.deleteQuestionWithId(answer.getId());
		}
		return "redirect:/fillQuestions";
	}
	
	@RequestMapping(value = "/matches", method = RequestMethod.GET)
	public String matches(Model model) {
		List<ProfilesModel> list = profileDao.getProfiles();
		if (profileDao.getProfileByUsername(getUsername()).getAnswers().isEmpty()) {
			model.addAttribute("errorMessage", "Please answer our questionnaire first!");
			return "forward:profile";
		}
		List<Float> percentages = new ArrayList<Float>();
		for (ProfilesModel profile : list) {
			Set<AnswersModel> answersOwn = profileDao.getProfileByUsername(getUsername()).getAnswers();
			Set<AnswersModel> answersMatch = profile.getAnswers();
			List<AnswersModel> answersOwnList = new ArrayList<AnswersModel>(answersOwn);
			List<AnswersModel> answersMatchList = new ArrayList<AnswersModel>(answersMatch);
			int questionPosition = new Integer(0);
			float matchCounter = new Float(0);
			int minimumAnswered = new Integer(0);
			if (answersOwnList.size() < answersMatchList.size())
				minimumAnswered = answersOwnList.size();
			else
				minimumAnswered = answersMatchList.size();
			while (questionPosition < minimumAnswered) {
				if (answersOwnList.get(questionPosition).isAnswer() == answersMatchList.get(questionPosition).isAnswer()) {
					matchCounter = matchCounter + 1;
				}
				questionPosition = questionPosition + 1;				
			}
			float percentage = (matchCounter / minimumAnswered) * 100;
			percentages.add(percentage);
		}
		model.addAttribute("profiles", list);
		model.addAttribute("percentages", percentages);
		return "matches";
	}
	
	
/*	VERSION 2.0 without own User results in Out of Bounds Exception even though both lists have the same length
 
  	public List<ProfilesModel> RemoveMePls(List<ProfilesModel> list) {
		for (ProfilesModel profile : list) {
			if(profileDao.getProfileByUsername(getUsername()).getId() == profile.getId()) {
				list.remove(profile);
			}
		}
		return list;
	}
 
	@RequestMapping(value = "/matches", method = RequestMethod.GET)
	public String matches(Model model) {
		List<ProfilesModel> list = RemoveMePls(profileDao.getProfiles());
		if (profileDao.getProfileByUsername(getUsername()).getAnswers().isEmpty()) {
			model.addAttribute("errorMessage", "Please answer our questionnaire first!");
			return "forward:profile";
		}
		List<Float> percentages = new ArrayList<Float>();
		for (ProfilesModel profile : list) {
			Set<AnswersModel> answersOwn = profileDao.getProfileByUsername(getUsername()).getAnswers();
			Set<AnswersModel> answersMatch = profile.getAnswers();
			List<AnswersModel> answersOwnList = new ArrayList<AnswersModel>(answersOwn);
			List<AnswersModel> answersMatchList = new ArrayList<AnswersModel>(answersMatch);
			int questionPosition = new Integer(0);
			float matchCounter = new Float(0);
			int minimumAnswered = new Integer(0);
			if (answersOwnList.size() < answersMatchList.size())
				minimumAnswered = answersOwnList.size();
			else
				minimumAnswered = answersMatchList.size();
			while (questionPosition < minimumAnswered) {
				if (answersOwnList.get(questionPosition).isAnswer() == answersMatchList.get(questionPosition).isAnswer()) {
					matchCounter = matchCounter + 1;
				}
				questionPosition = questionPosition + 1;				
			}
			float percentage = (matchCounter / minimumAnswered) * 100;
			percentages.add(percentage);
		}
		System.out.println(list.size());
		System.out.println(percentages);
		model.addAttribute("profiles", list);
		model.addAttribute("percentages", percentages);
		return "matches";
	}
	*/

	/*
	 * @RequestMapping(value = "/matches", method = RequestMethod.GET) public String
	 * matches(Model model) { List<ProfilesModel> profiles =
	 * profileDao.findByUsername(getUsername()); int percentage = 95;
	 * System.out.println(profileDao.getProfileByUsername(getUsername()).getAnswers(
	 * )); model.addAttribute("profiles",profiles); model.addAttribute("percentage",
	 * percentage); return "matches"; }
	 */

	@RequestMapping("/fillQuestions")
	@Transactional
	public String fillQuestions(Model model) {

		if (questionDao.isTableEmpty()) {
			QuestionModel q1 = new QuestionModel("Sport oder Schlafen? [sport/zzzzZZ]");
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
		return "forward:/question";
	}

	/*
	 * @ExceptionHandler(Exception.class) public String handleAllException(Exception
	 * ex) {
	 * 
	 * return "error";
	 * 
	 * }
	 */
}
