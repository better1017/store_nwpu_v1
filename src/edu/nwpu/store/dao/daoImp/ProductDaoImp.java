package edu.nwpu.store.dao.daoImp;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import edu.nwpu.store.dao.ProductDao;
import edu.nwpu.store.domain.Product;
import edu.nwpu.store.utils.JDBCUtils;

public class ProductDaoImp implements ProductDao {

	@Override
	public List<Product> findHots() throws SQLException {
		String sql = "SELECT * FROM product WHERE pflag=0 AND is_hot=1 ORDER BY pdate DESC LIMIT 0, 9";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public List<Product> findNews() throws SQLException {
		String sql = "SELECT * FROM product WHERE pflag=0 ORDER BY pdate DESC LIMIT 0, 9";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public Product findProductByPid(String pid) throws SQLException {
		String sql = "SELECT * FROM product WHERE pid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanHandler<Product>(Product.class), pid);
	}

}
