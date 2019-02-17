package edu.nwpu.store.dao.daoImp;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import edu.nwpu.store.dao.OrderDao;
import edu.nwpu.store.domain.Order;
import edu.nwpu.store.domain.OrderItem;
import edu.nwpu.store.domain.Product;
import edu.nwpu.store.domain.User;
import edu.nwpu.store.utils.JDBCUtils;

public class OrderDaoImp implements OrderDao {

	@Override
	public void saveOrder(Connection conn, Order order) throws SQLException {
		// System.out.println("OrderDaoImp");
		String sql = "INSERT INTO orders VALUES(?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner();
		Object[] params = { order.getOid(), order.getOrderTime(), order.getTotal(), order.getState(),
				order.getAddress(), order.getName(), order.getTelephone(), order.getUser().getUid() };
		qr.update(conn, sql, params);
		//System.out.println("end3");
	}

	@Override
	public void saveOrderItem(Connection conn, OrderItem item) throws Exception {
		String sql = "INSERT INTO orderitem VALUES(?,?,?,?,?)";
		QueryRunner qr = new QueryRunner();
		Object[] params = { item.getItemid(), item.getQuantity(), item.getTotal(), item.getProduct().getPid(),
				item.getOrder().getOid() };
		qr.update(conn, sql, params);
	}

	@Override
	public int getTotalRecords(User user) throws Exception {
		String sql = "SELECT COUNT(*) FROM orders WHERE uid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Long num = (Long) qr.query(sql, new ScalarHandler(), user.getUid());
		return num.intValue();
	}

	@Override
	public List<Order> findMyOrdersWithPage(User user, int startIndex, int pageSize) throws Exception {
		String sql = "SELECT * FROM orders WHERE uid=? LIMIT ?,?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Order> list = qr.query(sql, new BeanListHandler<Order>(Order.class), user.getUid(), startIndex, pageSize);

		// 遍历所有的订单
		for (Order order : list) {
			// 获取到每笔订单oid 查询每笔订单项以及订单项对应的商品信息
			String oid = order.getOid();
			sql = "SELECT * FROM orderitem o, product p WHERE o.pid=p.pid AND o.oid=?";
			List<Map<String, Object>> list2 = qr.query(sql, new MapListHandler(), oid);
			// 遍历list2
			for (Map<String, Object> map : list2) {
				OrderItem orderItem = new OrderItem();
				Product product = new Product();

				// 由于BeanUtils将时间字符串传递参数有问题，手动向BeanUtils注册一个时间转换器
				// 1_创建时间类型转换器
				DateConverter dt = new DateConverter();
				// 2_设置转换的格式
				dt.setPattern("yyyy-MM-dd");
				// 3_注册转换器
				ConvertUtils.register(dt, Date.class);

				// 将map中属于OrderItem的数据自动填充到orderItem对象上
				BeanUtils.populate(orderItem, map);
				// 将map中属于Product的数据自动填充到product对象上
				BeanUtils.populate(product, map);

				// 将商品与订单项关联
				orderItem.setProduct(product);
				// 将订单项与订单关联
				order.getList().add(orderItem);

			}

		}

		return list;
	}

	@Override
	public Order findOrderByOid(String oid) throws Exception {
		String sql = "SELECT * FROM orders WHERE oid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Order order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);

		// 根据订单id查询订单下所有的订单项以及订单项对应的商品信息
		sql = "SELECT * FROM orderitem o, product p WHERE o.pid=p.pid AND o.oid=?";
		List<Map<String, Object>> list2 = qr.query(sql, new MapListHandler(), oid);
		// 遍历list2
		for (Map<String, Object> map : list2) {
			OrderItem orderItem = new OrderItem();
			Product product = new Product();

			// 由于BeanUtils将时间字符串传递参数有问题，手动向BeanUtils注册一个时间转换器
			// 1_创建时间类型转换器
			DateConverter dt = new DateConverter();
			// 2_设置转换的格式
			dt.setPattern("yyyy-MM-dd");
			// 3_注册转换器
			ConvertUtils.register(dt, Date.class);

			// 将map中属于OrderItem的数据自动填充到orderItem对象上
			BeanUtils.populate(orderItem, map);
			// 将map中属于Product的数据自动填充到product对象上
			BeanUtils.populate(product, map);

			// 将商品与订单项关联
			orderItem.setProduct(product);
			// 将订单项与订单关联
			order.getList().add(orderItem);

		}

		return order;
	}

	@Override
	public void updateOrder(Order order) throws Exception {
		//System.out.println(order.toString());
		String sql = "UPDATE orders SET ordertime=?,total=?,state=?,address=?,name=?,telephone=?,uid=? WHERE oid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = { order.getOrderTime(), order.getTotal(), order.getState(), order.getAddress(), 
				order.getName(), order.getTelephone(), order.getUser().getUid(), order.getOid() };
		
		qr.update(sql, params);
	}

}
