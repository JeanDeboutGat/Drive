package fr.eservices.drive.web.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CartEntry {
	
	int id;
	int qty;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}

}
