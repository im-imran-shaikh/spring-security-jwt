package in.learnjavaskills.springsecurity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.learnjavaskills.springsecurity.entity.UserdetailsEntity;

@Repository
public interface UserdetailsRepository extends JpaRepository<UserdetailsEntity, Long> {

	List<UserdetailsEntity> findByUseremail(String useremail);
	
	boolean existsByuseremail(String useremail);
}
