package swenga.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import swenga.model.ProfilesModel;

public interface ProfileRepo extends JpaRepository<ProfilesModel, Integer>{

}
