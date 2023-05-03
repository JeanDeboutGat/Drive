package fr.eservices.drive.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "article")
public class Article {

    @Id
    int id;
    String
            name,
            img;

    int price;

    @OneToOne()
    @JoinColumn(name = "id_association")
    private OrderEntry orderentry;


    public Article() {
    }

    public Article(int id, String name, String img, int price) {
        super();
        this.id = id;
        this.name = name;
        this.img = img;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean equals(Object O) {
        if (O != null && O instanceof Article) {
            Article article = (Article) O;
            return article.getId() == this.getId();
        }
        return false;
    }

    public int hashCode() {
        int hash = 42;
        hash += id * 30;
        hash += name.hashCode() * 30;
        return hash;
    }

    public String toString() {
        return "Id : " + this.getId() + ", name: " + this.getName() + ",price :" + this.getPrice() + ",img :" + this.getImg();
    }
}
