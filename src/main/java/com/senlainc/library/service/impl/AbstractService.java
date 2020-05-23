package com.senlainc.library.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.senlainc.library.dao.GenericDAO;
import com.senlainc.library.entity.Model;
import com.senlainc.library.service.GenericService;

public abstract class AbstractService<E extends Model, D extends GenericDAO<E>> implements GenericService<E> {
	
	protected final D repository;

    @Autowired
    public AbstractService(D repository) {
        this.repository = repository;
    }
    
    @Override
    public Optional<E> save(E entity) {
    	return repository.save(entity);
    }
    
    @Override
    public Optional<E> update(E entity) {
    	return repository.update(entity);
    }
    
    @Override
    public Optional<E> get(Integer id) {
    	return repository.get(id);
    }
    
    @Override
    public List<E> getAll() {
    	return repository.getAll();
    }
    
    @Override
    public void delete(E entity) {
    	repository.delete(entity);
    }
    
    @Override
    public Boolean deleteById(Integer id) {
    	return repository.deleteById(id);
    }

}
