package edu.nwpu.store.service.serviceIml;

import java.sql.SQLException;
import java.util.List;

import edu.nwpu.store.dao.ProductDao;
import edu.nwpu.store.dao.daoImp.ProductDaoImp;
import edu.nwpu.store.domain.PageModel;
import edu.nwpu.store.domain.Product;
import edu.nwpu.store.service.ProductService;
import edu.nwpu.store.utils.BeanFactory;

public class ProductServiceImp implements ProductService {

	ProductDao dao = (ProductDao) BeanFactory.creatObject("ProductDao");

	@Override
	public List<Product> findHots() throws SQLException {
		return dao.findHots();
	}

	@Override
	public List<Product> findNews() throws SQLException {
		return dao.findNews();
	}

	@Override
	public Product findProductByPid(String pid) throws SQLException {
		return dao.findProductByPid(pid);
	}

	@Override
	public PageModel findProductsByCidWithPage(String cid, int curNum) throws SQLException {
		// 1_创建PageModel对象：计算分页参数
		// 统计当前分类下商品个数SELECT * FROM product WHERE cid=?
		int totalRecords = dao.findTotalRecords(cid);
		PageModel pm = new PageModel(curNum, 12, totalRecords);
		// 2_关联集合SELECT * FROM product WHERE cid=? LIMIT ?, ?
		List list = dao.findProductsByCidWithPage(cid, pm.getStartIndex(), pm.getPageSize());
		pm.setList(list);
		// 3_关联url
		pm.setUrl("ProductServlet?method=findProductsByCidWithPage&cid=" + cid);
		return pm;
	}

	@Override
	public PageModel findAllProductsWithPage(int curNum) throws SQLException {
		int totalRecords = dao.findTotalRecords();
		// 1_创建PageModel对象
		PageModel pm = new PageModel(curNum, 5, totalRecords);
		// 2_关联集合SELECT * FROM product LIMIT ?,?
		List list = dao.findAllProductsWithPage(pm.getStartIndex(), pm.getPageSize());
		pm.setList(list);
		// 3_关联URL
		pm.setUrl("AdminProductServlet?method=findAllProductsWithPage");
		return pm;
	}

	@Override
	public void saveProduct(Product product) throws SQLException {
		dao.saveProduct(product);
	}

}
