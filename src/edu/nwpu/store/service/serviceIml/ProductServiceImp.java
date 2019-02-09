package edu.nwpu.store.service.serviceIml;

import java.sql.SQLException;
import java.util.List;

import edu.nwpu.store.dao.ProductDao;
import edu.nwpu.store.dao.daoImp.ProductDaoImp;
import edu.nwpu.store.domain.Product;
import edu.nwpu.store.service.ProductService;

public class ProductServiceImp implements ProductService {

	ProductDao dao = new ProductDaoImp();
	
	@Override
	public List<Product> findHots() throws SQLException {
		return dao.findHots();
	}

	@Override
	public List<Product> findNews() throws SQLException {
		return dao.findNews();
	}

}
