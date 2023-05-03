package fr.eservices.drive.repository;

import fr.eservices.drive.util.ClaimStatus;
import fr.eservices.drive.model.Claim;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClaimRepository extends CrudRepository<Claim, Long> {
    List<Claim> findByCustomerId(UUID customerId);
    List<Claim> findByStatus(ClaimStatus status);
    Claim findByOrderId(long orderId);
}
