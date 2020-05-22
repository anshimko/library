package com.senlainc.library.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.senlainc.library.constraint.Unique;

@Entity
@Table(name = "catalogs")
@Unique(names = {"name"})
public class Catalog extends Model{
	
	private static final long serialVersionUID = 3962204870182456153L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", referencedColumnName = "id")
	private Catalog parentCatalog;

	@ManyToMany(mappedBy = "catalogs", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST}) // THIS IS EAGER 
	private Set<Book> books = new HashSet<Book>();
	
	@Column(name = "name", nullable = false, unique = true)
	private String name;

	public Catalog() {
		super();
	}

	public Catalog(Catalog parentCatalog, Set<Book> books, String name) {
		super();
		this.parentCatalog = parentCatalog;
		this.books = books;
		this.name = name;
	}

	public Catalog getParentCatalog() {
		return parentCatalog;
	}

	public void setParentCatalog(Catalog parentCatalog) {
		this.parentCatalog = parentCatalog;
	}

	//@JsonIgnore
	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
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
		result = prime * result + ((parentCatalog == null) ? 0 : parentCatalog.hashCode());
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
		Catalog other = (Catalog) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parentCatalog == null) {
			if (other.parentCatalog != null)
				return false;
		} else if (!parentCatalog.equals(other.parentCatalog))
			return false;
		return true;
	}


}
