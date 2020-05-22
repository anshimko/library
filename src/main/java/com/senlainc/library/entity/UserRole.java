package com.senlainc.library.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.senlainc.library.constraint.Unique;

@Entity
@Table(name = "user_role")
@Unique(names = {"name"})
public class UserRole extends Model {

	private static final long serialVersionUID = 5007545645153914019L;

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	public UserRole() {
		super();
	}

	public UserRole(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		UserRole other = (UserRole) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
