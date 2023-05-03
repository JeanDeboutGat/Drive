package fr.eservices.drive.repository;

import org.springframework.data.repository.CrudRepository;

import fr.eservices.drive.model.User;

public interface LoginRepository extends CrudRepository<User, Long> {
	
	User findByEmailAndPassword(String email, String password);

}
