package com.senlainc.library.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.senlainc.library.constraint.Unique;

@Entity
@Table(name = "users_info")
@Unique(names = {"email"})
public class UserInfo extends Model{
	
	private static final long serialVersionUID = 4246732946965936555L;

	@NotBlank (message = "Name must not be empty")
	@Column(name = "name")
	private String name;
	
	@NotBlank (message = "Surname must not be empty")
	@Column(name = "surname")
	private String surname;
	
	@Email (message = "Email must be a valid email address")
	@Column(name = "email", unique = true)
	private String email;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_id")
	private User user;

	public UserInfo() {
		super();
	}

	public UserInfo(String name, String surname, String email) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.user = user;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInfo other = (UserInfo) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
