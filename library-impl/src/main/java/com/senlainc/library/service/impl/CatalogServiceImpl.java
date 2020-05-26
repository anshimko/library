package com.senlainc.library.service.impl;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senlainc.library.dao.CatalogDAO;
import com.senlainc.library.entity.Catalog;
import com.senlainc.library.service.CatalogService;

@Service
@Transactional
public class CatalogServiceImpl extends AbstractService<Catalog, CatalogDAO> implements CatalogService{
	
	public CatalogServiceImpl(CatalogDAO repository) {
		super(repository);
	}

	@Override
	@Secured("ROLE_ADMIN")
	public void addBookInCatalog(Integer catalogId, Integer bookId) {
		repository.addBookInCatalog(catalogId, bookId);
		
	}

}
