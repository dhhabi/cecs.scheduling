package org.csulb.cecs.domain.account;
/**
 * 
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "account")
@Inheritance(strategy=InheritanceType.JOINED)
public class Account {
	
	public Account(){
		
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
	private Long id;
	
	@NotEmpty(message="Username is required")
	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Size(min = 6, message = "Password must be at least 6 characters")
	@Column(name = "password", nullable = false)
	private String password;

	@NotEmpty(message="First name is required")
	private String firstName;

	@NotEmpty(message="Last name is required")
	private String lastName;
	
	@Column(name = "role", nullable = false)
	private String role;
	
	@Column(name = "disabled")
	private boolean disabled = false;

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public Account(String username, String password, String firstName, String lastName, String role) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public String getRole() {
		return role;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
}
