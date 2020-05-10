package com.senlainc.library.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.senlainc.library.constraint.Unique;

@Entity
@Table(name = "users")
@Unique(names = {"login"})
public class User extends Model {

	private static final long serialVersionUID = -4273276363939591585L;

	@NotBlank (message = "Login must not be empty")
	@Column(name = "login")
	private String login;

	@NotBlank (message = "Password must not be empty")
	@Column(name = "password")
	private String password;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "user_role_id")
	private UserRole role;

	@OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private UserInfo userInfo;

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<RentHistory> rentHistories = new ArrayList<RentHistory>();

	public User() {
		super();
	}

	public User(String login, String password, UserRole role, UserInfo userInfo, List<RentHistory> rentHistories) {
		super();
		this.login = login;
		this.password = password;
		this.role = role;
		this.userInfo = userInfo;
		this.rentHistories = rentHistories;
	}

	@JsonIgnore
	public List<UserRole> getRoles() {
		List<UserRole> roles = new ArrayList<UserRole>();
		roles.add(this.getRole());
		return roles;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@JsonIgnore
	public List<RentHistory> getRentHistories() {
		return rentHistories;
	}

	public void setRentHistories(List<RentHistory> rentHistories) {
		this.rentHistories = rentHistories;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((rentHistories == null) ? 0 : rentHistories.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((userInfo == null) ? 0 : userInfo.hashCode());
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
		User other = (User) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (rentHistories == null) {
			if (other.rentHistories != null)
				return false;
		} else if (!rentHistories.equals(other.rentHistories))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (userInfo == null) {
			if (other.userInfo != null)
				return false;
		} else if (!userInfo.equals(other.userInfo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", password=" + password + ", role=" + role + ", userInfo=" + userInfo
				+ ", rentHistories=" + rentHistories + ", getId()=" + getId() + "]";
	}

	
	

}
