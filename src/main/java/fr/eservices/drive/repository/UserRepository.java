package fr.eservices.drive.repository;

import fr.eservices.drive.model.User;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByEmail(String email);
	
	User findById(UUID id);
}
