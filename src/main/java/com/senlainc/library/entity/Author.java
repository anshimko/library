package com.senlainc.library.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "authors")
public class Author extends Model {

	private static final long serialVersionUID = -875283374841894672L;

	@Column(name = "name")
	private String name;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "books_authors", 
		joinColumns = @JoinColumn(name = "authors_id"), 
		inverseJoinColumns = @JoinColumn(name = "books_id"))
	private Set<Book> books;

	public Author() {
		super();
	}

	public Author(String name, Set<Book> books) {
		super();
		this.name = name;
		this.books = books;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Book> getBooks() {
		return books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((books == null) ? 0 : books.hashCode());
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
		Author other = (Author) obj;
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
		return true;
	}
	
	
	
	

}
