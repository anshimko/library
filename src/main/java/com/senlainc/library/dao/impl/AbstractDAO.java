package com.senlainc.library.dao.impl;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.senlainc.library.dao.GenericDAO;
import com.senlainc.library.entity.Model;
import com.senlainc.library.exception.RecordNotFoundException;

public abstract class AbstractDAO <E extends Model> implements GenericDAO<E>{
	
	private Class<E> clazz;
	
	protected final SessionFactory sessionFactory;
	
	@Autowired
	public AbstractDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public Optional<E> save(E entity) {
		
		getCurrentSession().saveOrUpdate(entity);
		Optional<E> optional = Optional.ofNullable(getCurrentSession().get(clazz, entity.getId()));
		return optional;
	}
	
	@Override
	public Optional<E> update(E entity) {
		
		Optional<E> optional = Optional.ofNullable((E)getCurrentSession().merge(entity));
		return optional;
	}
	
	@Override
	public Optional<E> get(Integer id) {
		
		E entity = getCurrentSession().get(clazz, id);
		if(entity == null) {
			throw new RecordNotFoundException(clazz.getSimpleName() + " id '" + id + "' does no exist");
		}
		
		return Optional.of(entity);
	}
	
	@Override
	public List<E> getAll() {
		return getCurrentSession().createQuery("from " + clazz.getName()).list();
	}
	
	@Override
	public void delete(E entity) {
        getCurrentSession().delete(entity);
    }
	
	@Override
	public Boolean deleteById(Integer id) {
		E entity = get(id).get();
        delete(entity);
		return true;
	}
	
	public void setClazz(Class<E> clazzToSet){
	       this.clazz = clazzToSet;
	    }
	
	protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
