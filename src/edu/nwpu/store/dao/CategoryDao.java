package edu.nwpu.store.dao;

import java.sql.SQLException;
import java.util.List;

import edu.nwpu.store.domain.Category;

public interface CategoryDao {

	public List<Category> getAllCategories() throws SQLException;

	public void addCategory(Category category) throws SQLException;

}
