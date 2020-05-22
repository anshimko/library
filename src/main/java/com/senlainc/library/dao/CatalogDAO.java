package com.senlainc.library.dao;

import com.senlainc.library.entity.Catalog;

public interface CatalogDAO {

	Catalog create(Catalog catalog);

	Catalog read(Integer id);

	boolean delete(Integer id);

	void addBookInCatalog(Integer catalogId, Integer bookId);

}
