package edu.nwpu.store.service.serviceIml;

import java.sql.SQLException;
import java.util.List;

import edu.nwpu.store.dao.CategoryDao;
import edu.nwpu.store.dao.daoImp.CategoryDaoImp;
import edu.nwpu.store.domain.Category;
import edu.nwpu.store.service.CategoryService;
import edu.nwpu.store.utils.JedisUtils;
import redis.clients.jedis.Jedis;

public class CategoryServiceImp implements CategoryService {
	CategoryDao dao = new CategoryDaoImp();

	@Override
	public List<Category> getAllCategories() throws SQLException {

		return dao.getAllCategories();
	}

	@Override
	public void addCategory(Category category) throws SQLException {
		//本质是向MySQL数据库插入一条数据
		dao.addCategory(category);
		//更新redis缓存
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCats");
	}
}