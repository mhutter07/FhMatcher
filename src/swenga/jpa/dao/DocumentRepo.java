package swenga.jpa.dao;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import swenga.model.DocumentModel;
import swenga.model.ProfilesModel;

@Repository
@Transactional
public interface DocumentRepo extends JpaRepository<DocumentModel, Integer>{

	List<DocumentModel> findAllByName(ProfilesModel profile);


	

}
