package swenga.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import swenga.model.ProfilesModel;

@Repository
@Transactional
public class ProfileDao {
	
	public static List<ProfilesModel> findById;
	@PersistenceContext
	protected EntityManager entityManager;
 
	public List<ProfilesModel> getProfiles() {
		TypedQuery<ProfilesModel> typedQuery = entityManager.createQuery("select p from ProfilesModel p",
				ProfilesModel.class);
		List<ProfilesModel> typedResultList = typedQuery.getResultList();
		return typedResultList;
	}
 
	public List<ProfilesModel> searchProfiles(String searchString) {
		TypedQuery<ProfilesModel> typedQuery = entityManager.createQuery(
				"select p from ProfilesModel p where p.firstname like :search or p.lastname like :search",
				ProfilesModel.class);
		typedQuery.setParameter("search", "%" + searchString + "%");
		List<ProfilesModel> typedResultList = typedQuery.getResultList();
		return typedResultList;
	}
	
	public List<ProfilesModel> requestProfile(String username){
		TypedQuery<ProfilesModel> typedQuery = entityManager.createQuery(
				"select p from ProfilesModel p where p.userName like :name", ProfilesModel.class);
		typedQuery.setParameter("name", username);
		List<ProfilesModel> typedResultList = typedQuery.getResultList();
		return typedResultList;
		
	}
 
	public ProfilesModel getProfiles(int i) throws DataAccessException {
		return entityManager.find(ProfilesModel.class, i);
	}
 
	public ProfilesModel merge(ProfilesModel profile) {
		return entityManager.merge(profile);
	}
 
	public void delete(ProfilesModel profile) {
		entityManager.remove(profile);
	}
 
	public int deleteAllProfiles() {
		int count = entityManager.createQuery("DELETE FROM ProfilesModel").executeUpdate();
		return count;
	}
 
	public void delete(int id) {
		ProfilesModel profile = getProfiles(id);
		if (profile != null)
			delete (profile);
	}

	public static List<ProfilesModel> findByName(String firstname) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ProfilesModel> findByUsername(String userName) {
		TypedQuery<ProfilesModel> typedQuery = entityManager.createQuery("select p from ProfilesModel p where p.userName = :name",
				ProfilesModel.class);
		typedQuery.setParameter("name", userName);
		List<ProfilesModel> typedResultList = typedQuery.getResultList();
		return typedResultList;
	}

	public void persist(ProfilesModel user) {
		entityManager.persist(user);
	}
	

	
}


