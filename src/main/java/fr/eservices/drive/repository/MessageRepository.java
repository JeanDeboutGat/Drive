package fr.eservices.drive.repository;

import fr.eservices.drive.model.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
