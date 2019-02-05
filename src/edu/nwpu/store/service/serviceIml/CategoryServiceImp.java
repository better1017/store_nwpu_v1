package edu.nwpu.store.service.serviceIml;

import java.sql.SQLException;
import java.util.List;

import edu.nwpu.store.dao.CategoryDao;
import edu.nwpu.store.dao.daoImp.CategoryDaoImp;
import edu.nwpu.store.domain.Category;
import edu.nwpu.store.service.CategoryService;

public class CategoryServiceImp implements CategoryService {

	@Override
	public List<Category> getAllCategories() throws SQLException {
		CategoryDao dao = new CategoryDaoImp();
		return dao.getAllCategories();
	}

}
