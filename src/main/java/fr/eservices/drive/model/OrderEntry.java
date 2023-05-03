package fr.eservices.drive.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="orderEntry")
public class OrderEntry implements Serializable {
	
	@Id
    @GeneratedValue
    private int id;	
	
	@ManyToOne()
    @JoinColumn(name = "id_order")
    private Order order;
	
	@OneToOne()
    @JoinColumn(name = "id_article")
    private Article article;


	int quantity;
	
	public OrderEntry(Article article,Order order,int quantity) {
        this.order = order;
        this.article = article;
        this.quantity = quantity;
        
    }

	public OrderEntry() {}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public String toString() {
		return "OrderEntry [id=" + id + ", order=" + order + ", article=" + article + ", quantity=" + quantity + "]";
	}
}
