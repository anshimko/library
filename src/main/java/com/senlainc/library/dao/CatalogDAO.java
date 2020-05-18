package com.senlainc.library.dao;

import com.senlainc.library.entity.Catalog;

public interface CatalogDAO {

	void create(Catalog catalog);

	Catalog read(int id);

	boolean delete(int id);

	void addBookInCatalog(int catalogId, int bookId);

}
