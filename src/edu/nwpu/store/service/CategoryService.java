package edu.nwpu.store.service;

import java.sql.SQLException;
import java.util.List;

import edu.nwpu.store.domain.Category;

public interface CategoryService {

	public List<Category> getAllCategories() throws SQLException;

}
