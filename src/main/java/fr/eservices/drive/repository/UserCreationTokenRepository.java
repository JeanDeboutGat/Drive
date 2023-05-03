package fr.eservices.drive.repository;

import org.springframework.data.repository.CrudRepository;

import fr.eservices.drive.model.User;
import fr.eservices.drive.model.UserCreationToken;

public interface UserCreationTokenRepository extends CrudRepository<UserCreationToken, Long> {
	
	UserCreationToken findByToken(String token);
	
	User findByUserId(long userId);
}
