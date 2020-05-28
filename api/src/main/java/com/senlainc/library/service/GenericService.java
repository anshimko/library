package com.senlainc.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import com.senlainc.library.entity.Model;
import com.senlainc.library.search.SearchCriteria;

@Transactional
public interface GenericService <E extends Model>{
	
	@Secured("ROLE_ADMIN")
	Optional<E> save(E entity);
	
	@Secured("ROLE_ADMIN")
    Optional<E> update(E entity);

    @Secured({"ROLE_ADMIN", "ROLE_READER"})
    Optional<E> get(Integer id);

    @Secured({"ROLE_ADMIN", "ROLE_READER"})
    List<E> getAll(int page, int size, List<SearchCriteria> params);
    
	@Secured("ROLE_ADMIN")
    void delete(E entity);
	
	@Secured("ROLE_ADMIN")
    Boolean deleteById(Integer id);

}
