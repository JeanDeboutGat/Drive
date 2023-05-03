package fr.eservices.drive.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.eservices.drive.util.Role;

import java.util.Date;

@Entity(name = "messages")
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue
    long id;
    Role source;
    String content;
    Date date;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Role getSource() {
        return source;
    }

    public void setSource(Role source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
