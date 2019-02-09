package edu.nwpu.store.dao;

import java.sql.SQLException;
import java.util.List;

import edu.nwpu.store.domain.Product;

public interface ProductDao {

	List<Product> findHots() throws SQLException;

	List<Product> findNews() throws SQLException;

}
