package com.senlainc.library.dao;

import java.util.List;
import java.util.Optional;

import com.senlainc.library.entity.Model;
import com.senlainc.library.search.SearchCriteria;

public interface GenericDAO <E extends Model>{
	
	Optional<E> save(E entity);

    Optional<E> update(E entity);

    Optional<E> get(Integer id);

    List<E> getAll(int page, int size, List<SearchCriteria> params);
    
    void delete(E entity);

    Boolean deleteById(Integer id);

}
