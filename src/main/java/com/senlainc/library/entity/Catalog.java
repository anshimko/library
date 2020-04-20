package com.senlainc.library.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "catalogs")
public class Catalog extends Model{
	
	private static final long serialVersionUID = 3962204870182456153L;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", referencedColumnName = "id")
	private Catalog parentCatalog;

	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "books_catalogs", 
		joinColumns = @JoinColumn(name = "catalogs_id"), 
		inverseJoinColumns = @JoinColumn(name = "books_id"))
	private Set<Book> books;
	
	@Column(name = "name")
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
		result = prime * result + ((books == null) ? 0 : books.hashCode());
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
		if (books == null) {
			if (other.books != null)
				return false;
		} else if (!books.equals(other.books))
			return false;
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
