package swenga.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
 
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import swenga.model.AnswersModel;
import swenga.model.PostModel;
import swenga.model.ProfilesModel;
import swenga.model.QuestionModel;

@Repository
@Transactional
public class PostDao {
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	public void persist(PostModel post) {
		entityManager.persist(post);
	}
	
	public PostModel getById(int i) throws DataAccessException {
		return entityManager.find(PostModel.class, i);
	}
	
	public List<PostModel> getPosts() {
		 
		TypedQuery<PostModel> typedQuery = entityManager.createQuery(
				"select q from PostModel q", PostModel.class);
		List<PostModel> typedResultList = typedQuery.getResultList();
		return typedResultList;
	}

}
