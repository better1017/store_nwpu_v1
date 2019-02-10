package edu.nwpu.store.service;

import java.sql.SQLException;
import java.util.List;

import edu.nwpu.store.domain.Product;

public interface ProductService {

	List<Product> findHots() throws SQLException;

	List<Product> findNews() throws SQLException;

	Product findProductByPid(String pid) throws SQLException;

}
