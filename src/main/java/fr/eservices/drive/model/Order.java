package fr.eservices.drive.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import fr.eservices.drive.util.Status;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue
    long id;

    UUID customerId;

    @Temporal(TemporalType.TIMESTAMP)
    Date createdOn;

    int amount;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Article> articles = new ArrayList<>();

    @OneToMany(targetEntity = OrderEntry.class, mappedBy = "order")
    private List<OrderEntry> orderentries;

    Status currentStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public int getAmount() {
        return amount;
    }

    public List<OrderEntry> getOrderentry() {
        return orderentries;
    }

    public void setOrderentry(List<OrderEntry> orderentry) {
        this.orderentries = orderentry;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Status getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Status currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String toString() {
        return "Order [id=" + id + "]";
    }

}
