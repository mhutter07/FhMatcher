package swenga.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.ResultSet;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import swenga.model.QuestionModel;

@Repository
@Transactional
public class QuestionDao {
	
	@PersistenceContext
	protected EntityManager entityManager;
 
	public List<QuestionModel> getQuestions() {
 
		TypedQuery<QuestionModel> typedQuery = entityManager.createQuery(
				"select q from QuestionModel q", QuestionModel.class);
		List<QuestionModel> typedResultList = typedQuery.getResultList();
		return typedResultList;
	}
 
	public QuestionModel getQuestion(String description) {
		try {
			TypedQuery<QuestionModel> typedQuery = entityManager.createQuery(
					"select q from QuestionModel q where q.description = :description",
					QuestionModel.class);
			typedQuery.setParameter("description", description);
			QuestionModel question = typedQuery.getSingleResult();
			return question;
		} catch (Exception ex) {
			return null;
		}
	}
	
	public QuestionModel getQuestionByID(int id) {
		try {
			TypedQuery<QuestionModel> typedQuery = entityManager.createQuery(
					"select q from QuestionModel q where q.id = :id",
					QuestionModel.class);
			typedQuery.setParameter("id", id);
			QuestionModel question = typedQuery.getSingleResult();
			return question;
		} catch (Exception ex) {
			return null;
		}
	}
	
	public void persist(QuestionModel question) {
		entityManager.persist(question);
	}
	
	public boolean isTableEmpty()
	   {   
		TypedQuery<QuestionModel> typedQuery = entityManager.createQuery(
				"select q from QuestionModel q", QuestionModel.class);
		List<QuestionModel> typedResultList = typedQuery.getResultList();
		
		if(typedResultList.isEmpty()) return true;
		
	      return false;   
	   }
}


