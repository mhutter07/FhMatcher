package swenga.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import swenga.model.DocumentModel;

@Repository
@Transactional
public interface DocumentRepo extends JpaRepository<DocumentModel, Integer>{


	

}
