package fr.eservices.drive.repository;

import fr.eservices.drive.model.Order;
import fr.eservices.drive.model.OrderEntry;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface OrderEntryRepository extends CrudRepository<OrderEntry, Long> {
	
	List<OrderEntry> findByOrder(Order order);
	
}
