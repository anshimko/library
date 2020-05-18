package com.senlainc.library.service;

import com.senlainc.library.entity.Catalog;

public interface CatalogService {

	void create(Catalog catalog);

	Catalog read(int id);

	boolean delete(int id);

	void addBookInCatalog(int catalogId, int bookId);

}
