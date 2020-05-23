package com.senlainc.library.dao;

import java.util.List;
import java.util.Optional;

import com.senlainc.library.entity.Model;

public interface GenericDAO <E extends Model>{
	
	Optional<E> save(E entity);

    Optional<E> update(E entity);

    Optional<E> get(Integer id);

    List<E> getAll();
    
    void delete(E entity);

    Boolean deleteById(Integer id);

}
