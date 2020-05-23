package com.senlainc.library.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.senlainc.library.dao.RentDAO;
import com.senlainc.library.entity.Book;
import com.senlainc.library.entity.BookReturnDTO;
import com.senlainc.library.entity.RentHistory;
import com.senlainc.library.entity.User;

@Repository
public class RentDAOImpl implements RentDAO {
	
	private static final String FIELD_ID = "id";
	private static final String FIELD_BOOK = "book";
	private static final String FIELD_TITLE = "title";
	private static final String FIELD_RETURNED_DATE = "returnDate";
	private static final String FIELD_RETURNED = "returned";
	
	private static final String TABLE_RENT_HISTORIES = "rentHistories";
	
	private static final String QUERY_RENT_IS_RETURNED = "UPDATE rent_history SET is_returned = true WHERE books_id = :book AND is_returned = false";

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<RentHistory> readByBook(Integer id) {

		Session session = sessionFactory.getCurrentSession();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<RentHistory> criteria = builder.createQuery(RentHistory.class);
		Root<RentHistory> root = criteria.from(RentHistory.class);
		criteria.select(root).where(builder.equal(root.get(FIELD_BOOK).get(FIELD_ID), id));

		List<RentHistory> rents = session.createQuery(criteria).getResultList();

		Book book = new Book();
		book.setId(id);

		for (RentHistory rent : rents) {
			int idUser = rent.getUser().getId();
			User user = new User();
			user.setId(idUser);
			rent.setUser(user);

			rent.setBook(book);
		}

		return rents;

	}

	@Override
	public void create(RentHistory rentHistory) {
		Session session = sessionFactory.getCurrentSession();

		rentHistory.getUser().getRentHistories().add(rentHistory);
		rentHistory.getBook().getRentHistories().add(rentHistory);

		session.saveOrUpdate(rentHistory);
	}

	@Override
	public boolean returned(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		
		int check = session.createNativeQuery(QUERY_RENT_IS_RETURNED)
					.setParameter(FIELD_BOOK, id)
					.executeUpdate();

		return (check == 1) ? true : false;
	}

	@Override
	public List<Book> readAvailable() {
		Session session = sessionFactory.getCurrentSession();
		
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Book> criteria = cb.createQuery(Book.class);
		Root<Book> root = criteria.from(Book.class);
		Join<Book, RentHistory> join = root.join(TABLE_RENT_HISTORIES);
		
		criteria.select(root).where(cb.equal(join.get(FIELD_RETURNED), true));
		
		List<Book> books = session.createQuery(criteria).getResultList();
		books.forEach(book -> Hibernate.initialize(book.getAuthors()));
		
		return books;
	}

	@Override
	public List<BookReturnDTO> readBorrow() {
		Session session = sessionFactory.getCurrentSession();
		
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BookReturnDTO> criteria = cb.createQuery(BookReturnDTO.class);
		Root<Book> root = criteria.from(Book.class);
		Join<Book, RentHistory> join = root.join(TABLE_RENT_HISTORIES);
		
		criteria.select(cb.construct(BookReturnDTO.class, root.get(FIELD_ID), root.get(FIELD_TITLE), join.get(FIELD_RETURNED_DATE)))
				.where(cb.equal(join.get(FIELD_RETURNED), false));
		
		List<BookReturnDTO> books = session.createQuery(criteria).getResultList();
		
		return books;
	}

	@Override
	public List<BookReturnDTO> readBorrowOverdue() {
		Session session = sessionFactory.getCurrentSession();
		
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<BookReturnDTO> criteria = cb.createQuery(BookReturnDTO.class);
		Root<Book> root = criteria.from(Book.class);
		Join<Book, RentHistory> join = root.join(TABLE_RENT_HISTORIES);
		
		criteria.select(cb.construct(BookReturnDTO.class, root.get(FIELD_ID), root.get(FIELD_TITLE), join.get(FIELD_RETURNED_DATE)));
		
		Predicate predicateForReturned = cb.equal(join.get(FIELD_RETURNED), false);
		Predicate predicateForReturnDate = cb.lessThan(join.get(FIELD_RETURNED_DATE), cb.currentDate());
		Predicate predicateForReturnedAndReturnDate = cb.and(predicateForReturned, predicateForReturnDate);
				
		criteria.where(predicateForReturnedAndReturnDate);
		
		List<BookReturnDTO> books = session.createQuery(criteria).getResultList();
		
		return books;
	}

}
