package fr.eservices.drive.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import fr.eservices.drive.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findByCustomerIdOrderByCreatedOnDesc(UUID custId);
    
    Order findById(long id);

    Order findByIdAndCustomerId(long id, UUID customerId);

}
