package com.senlainc.library.dao;

import com.senlainc.library.entity.Catalog;

public interface CatalogDAO extends GenericDAO<Catalog>{

	void addBookInCatalog(Integer catalogId, Integer bookId);

}
