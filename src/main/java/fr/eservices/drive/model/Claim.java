package fr.eservices.drive.model;

import javax.persistence.*;

import fr.eservices.drive.util.ClaimStatus;

import java.util.List;
import java.util.UUID;

@Entity(name = "claims")
public class Claim {
    @Id
    @GeneratedValue
    long id;

    UUID customerId;
    String object;
    long orderId;
    ClaimStatus status;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("date asc")
    List<Message> messages;

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String description) {
        this.object = description;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public ClaimStatus getStatus() {
        return status;
    }

    public void setStatus(ClaimStatus status) {
        this.status = status;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public List<Message> getMessages() {
        return messages;
    }
    public void addMessage(Message message) {
        this.messages.add(message);
    }
}
