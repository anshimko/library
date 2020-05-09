package com.senlainc.library.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.annotations.SQLUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "rent_history")
@SQLUpdate(sql = "update rent_history set books_id=?, borrow_date=?, return_date=?, is_returned=?, users_id=? where id=?")
public class RentHistory extends Model {

	private static final long serialVersionUID = -4993737000145245063L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "books_id")
	private Book book;

	@Column(name = "borrow_date")
	@Temporal(TemporalType.DATE)
	@PastOrPresent(message = "Date must be past or present")
	private Date borrowDate;

	@Column(name = "return_date")
	@Temporal(TemporalType.DATE)
	@FutureOrPresent(message = "Date must be future or present")
	private Date returnDate;

	@Column(name = "is_returned")
	private boolean returned;

	public RentHistory() {
		super();
	}
	
	public RentHistory(@FutureOrPresent(message = "Date must be future or present") Date returnDate) {
		super();
		this.returnDate = returnDate;
	}

	public RentHistory(int id, boolean returned) {
		super(id);
		this.returned = returned;
	}

	public RentHistory(User user, Book book, Date borrowDate, Date returnDate, boolean returned) {
		super();
		this.user = user;
		this.book = book;
		this.borrowDate = borrowDate;
		this.returnDate = returnDate;
		this.returned = returned;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	public Date getBorrowDate() {
		return borrowDate;
	}

	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public boolean isReturned() {
		return returned;
	}

	public void setReturned(boolean returned) {
		this.returned = returned;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((book == null) ? 0 : book.hashCode());
		result = prime * result + ((borrowDate == null) ? 0 : borrowDate.hashCode());
		result = prime * result + ((returnDate == null) ? 0 : returnDate.hashCode());
		result = prime * result + (returned ? 1231 : 1237);
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
		RentHistory other = (RentHistory) obj;
		if (book == null) {
			if (other.book != null)
				return false;
		} else if (!book.equals(other.book))
			return false;
		if (borrowDate == null) {
			if (other.borrowDate != null)
				return false;
		} else if (!borrowDate.equals(other.borrowDate))
			return false;
		if (returnDate == null) {
			if (other.returnDate != null)
				return false;
		} else if (!returnDate.equals(other.returnDate))
			return false;
		if (returned != other.returned)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RentHistory [user=" + user + ", book=" + book + ", borrowDate=" + borrowDate + ", returnDate="
				+ returnDate + ", returned=" + returned + ", getId()=" + getId() + "]";
	}

}
