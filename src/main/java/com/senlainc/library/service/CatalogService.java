package com.senlainc.library.service;

import com.senlainc.library.entity.Catalog;

public interface CatalogService {

	void create(Catalog catalog);

	Catalog read(Integer id);

	boolean delete(Integer id);

	void addBookInCatalog(Integer catalogId, Integer bookId);

}
