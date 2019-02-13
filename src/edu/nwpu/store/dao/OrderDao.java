package edu.nwpu.store.dao;

import java.sql.Connection;

import edu.nwpu.store.domain.Order;
import edu.nwpu.store.domain.OrderItem;

public interface OrderDao {

	public void saveOrder(Connection conn, Order order) throws Exception;

	public void saveOrderItem(Connection conn, OrderItem item) throws Exception;

}
