package edu.nwpu.store.dao.daoImp;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import edu.nwpu.store.dao.CategoryDao;
import edu.nwpu.store.domain.Category;
import edu.nwpu.store.utils.JDBCUtils;

public class CategoryDaoImp implements CategoryDao {

	@Override
	public List<Category> getAllCategories() throws SQLException {
		String sql = "SELECT * FROM category";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Category>(Category.class));
	}

	@Override
	public void addCategory(Category category) throws SQLException {
		String sql = "INSERT INTO category(cid,cname) VALUES (?,?)";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		qr.update(sql, category.getCid(), category.getCname());
	}
}
