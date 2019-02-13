package edu.nwpu.store.dao.daoImp;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import edu.nwpu.store.dao.OrderDao;
import edu.nwpu.store.domain.Order;
import edu.nwpu.store.domain.OrderItem;

public class OrderDaoImp implements OrderDao {

	@Override
	public void saveOrder(Connection conn, Order order) throws SQLException {
		System.out.println("OrderDaoImp");
		String sql = "INSERT INTO orders VALUES(?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner();
		Object[] params = { order.getOid(), order.getOrderTime(), order.getTotal(), order.getState(),
				order.getAddress(), order.getName(), order.getTelephone(), order.getUser().getUid() };
		qr.update(conn, sql, params);
		System.out.println("end3");
	}

	@Override
	public void saveOrderItem(Connection conn, OrderItem item) throws Exception {
		String sql = "INSERT INTO orderitem VALUES(?,?,?,?,?)";
		QueryRunner qr = new QueryRunner();
		Object[] params = { item.getItemid(), item.getQuantity(), item.getTotal(), item.getProduct().getPid(),
				item.getOrder().getOid() };
		qr.update(conn, sql, params);
	}

}
