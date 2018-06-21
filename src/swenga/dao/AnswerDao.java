package swenga.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
 
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import swenga.model.AnswersModel;

@Repository
@Transactional
public class AnswerDao {
	
	@PersistenceContext
	protected EntityManager entityManager;
 
	public List<AnswersModel> getAnswers() {
 
		TypedQuery<AnswersModel> typedQuery = entityManager.createQuery(
				"select a from AnswersModel a", AnswersModel.class);
		List<AnswersModel> typedResultList = typedQuery.getResultList();
		return typedResultList;
	}
	


}
