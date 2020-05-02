package com.senlainc.library.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "books")
public class Book extends Model {

	private static final long serialVersionUID = 563637046306602222L;

	@Column(name = "title")
	private String title;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "books_authors", 
			joinColumns = @JoinColumn(name = "books_id"), 
			inverseJoinColumns = @JoinColumn(name = "authors_id"))
	private Set<Author> authors = new HashSet<Author>();
	
	@OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
	private List<RentHistory> rentHistories = new ArrayList<RentHistory>();
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "books_catalogs", 
			joinColumns = @JoinColumn(name = "books_id"), 
			inverseJoinColumns = @JoinColumn(name = "catalogs_id"))
	private List<Catalog> catalogs = new ArrayList<Catalog>();

	public Book() {
		super();
	}

	public Book(String title, Set<Author> authors, List<RentHistory> rentHistories, List<Catalog> catalogs) {
		super();
		this.title = title;
		this.authors = authors;
		this.rentHistories = rentHistories;
		this.catalogs = catalogs;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	@JsonIgnore
	public List<RentHistory> getRentHistories() {
		return rentHistories;
	}

	public void setRentHistories(List<RentHistory> rentHistories) {
		this.rentHistories = rentHistories;
	}

	public List<Catalog> getCatalogs() {
		return catalogs;
	}

	public void setCatalogs(List<Catalog> catalogs) {
		this.catalogs = catalogs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Book other = (Book) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}
