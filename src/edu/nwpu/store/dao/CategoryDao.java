package edu.nwpu.store.dao;

import java.sql.SQLException;
import java.util.List;

import edu.nwpu.store.domain.Category;

public interface CategoryDao {

	public List<Category> getAllCategories() throws SQLException;

}
