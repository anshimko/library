package com.senlainc.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.senlainc.library.dao.CatalogDAO;
import com.senlainc.library.entity.Catalog;
import com.senlainc.library.service.CatalogService;

@Service
public class CatalogServiceImpl implements CatalogService{
	
	@Autowired
	private CatalogDAO catalogDAO;

	@Override
	@Secured("ROLE_ADMIN")
	@Transactional
	public void create(Catalog catalog) {
		catalogDAO.create(catalog);
		
	}

	@Override
	@Secured({"ROLE_ADMIN", "ROLE_READER"})
	@Transactional
	public Catalog read(int id) {
		return catalogDAO.read(id);
	}

	@Override
	@Secured("ROLE_ADMIN")
	@Transactional
	public boolean delete(int id) {
		return catalogDAO.delete(id);
	}

	@Override
	@Secured("ROLE_ADMIN")
	@Transactional
	public void addBookInCatalog(int catalogId, int bookId) {
		catalogDAO.addBookInCatalog(catalogId, bookId);
		
	}

}
