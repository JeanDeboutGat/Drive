package fr.eservices.drive.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import fr.eservices.drive.util.Role;
import fr.eservices.drive.util.PasswordChecker;
import fr.eservices.drive.util.SHA256Checker;

@Entity
@Table(name="user")
public class User {
	
	@Id
	UUID id;
	
	String firstName;

	String lastName;
	
	String email;
	
	String address;
	
	String password;
	
	Boolean isActive;
	
	Boolean isBanned;
	
	Role role;
	
	@Transient
	PasswordChecker pwdCheck;

	public User(String firstName, String lastName, String email, String address, String password) {
		super();
		this.id = UUID.randomUUID();
		this.pwdCheck = new SHA256Checker();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.password = this.pwdCheck.encode(password);
		this.isActive = false;
		this.role = Role.USER;
		this.isBanned = false;
	}
	
	public User() {
		
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = this.pwdCheck.encode(firstName);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = this.pwdCheck.encode(email);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = this.pwdCheck.encode(address);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = this.pwdCheck.encode(password);
	}

	public String getLastName() { return lastName; }

	public void setLastName(String lastName) { this.lastName = this.pwdCheck.encode(lastName); }
	
	public Boolean getIsActive() { return isActive; }

	public void setIsActive(Boolean isActive) { this.isActive = isActive; }	

	public Role getRole() { return role; }

	public void setRole(Role role) { this.role = role; }

	public String toString() {
		return "User [id=" + id + ", firstname=" + firstName + ", lastname=" + lastName + ", email=" + email + ", address=" + address + ", isActive=" + isActive + "]";
	}

	public Boolean getIsBanned() {
		return isBanned;
	}

	public void setIsBanned(Boolean isBanned) {
		this.isBanned = isBanned;
	}
	
	public String getStringfiedRole() {
		return this.role.toString();
	}
}
