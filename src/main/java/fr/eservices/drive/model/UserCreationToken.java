package fr.eservices.drive.model;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="user_creation_token")
public class UserCreationToken {
	
	@Id
	@GeneratedValue
	long rowId;
	
	UUID userId;
	
	String token;
	
	@Temporal(TemporalType.TIMESTAMP)
	Date creationTime;
	
	boolean is_used;
	
	public UserCreationToken(UUID user_id) {
		this.userId = user_id;
		this.creationTime = Date.from(Instant.now());
		this.is_used = false;
		this.token = UUID.randomUUID().toString().replace("-", "");
	}
	
	public UserCreationToken() {}

	public long getRowId() {
		return rowId;
	}

	public void setRowId(long rowId) {
		this.rowId = rowId;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public boolean isUsed() {
		return is_used;
	}

	public void setIsUsed(boolean is_used) {
		this.is_used = is_used;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
