package edu.nwpu.store.dao;

import java.sql.Connection;
import java.util.List;

import edu.nwpu.store.domain.Order;
import edu.nwpu.store.domain.OrderItem;
import edu.nwpu.store.domain.User;

public interface OrderDao {

	public void saveOrder(Connection conn, Order order) throws Exception;

	public void saveOrderItem(Connection conn, OrderItem item) throws Exception;

	public int getTotalRecords(User user) throws Exception;

	public List findMyOrdersWithPage(User user, int startIndex, int pageSize) throws Exception;

	public Order findOrderByOid(String oid) throws Exception;

	public void updateOrder(Order order) throws Exception;

	public List<Order> findAllOrders() throws Exception;

	public List<Order> findAllOrders(String state) throws Exception;

}
