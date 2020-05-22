package com.senlainc.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senlainc.library.dao.CatalogDAO;
import com.senlainc.library.entity.Catalog;
import com.senlainc.library.service.CatalogService;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService{
	
	@Autowired
	private CatalogDAO catalogDAO;

	@Override
	@Secured("ROLE_ADMIN")
	public Catalog create(Catalog catalog) {
		return catalogDAO.create(catalog);
		
	}

	@Override
	@Secured({"ROLE_ADMIN", "ROLE_READER"})
	public Catalog read(Integer id) {
		return catalogDAO.read(id);
	}

	@Override
	@Secured("ROLE_ADMIN")
	public boolean delete(Integer id) {
		return catalogDAO.delete(id);
	}

	@Override
	@Secured("ROLE_ADMIN")
	public void addBookInCatalog(Integer catalogId, Integer bookId) {
		catalogDAO.addBookInCatalog(catalogId, bookId);
		
	}

}
