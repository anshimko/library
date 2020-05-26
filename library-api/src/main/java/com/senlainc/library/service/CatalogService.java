package com.senlainc.library.service;

import com.senlainc.library.entity.Catalog;

public interface CatalogService extends GenericService<Catalog>{

	void addBookInCatalog(Integer catalogId, Integer bookId);

}
