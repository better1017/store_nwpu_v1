package edu.nwpu.store.service.serviceIml;

import java.sql.Connection;
import java.util.List;

import edu.nwpu.store.dao.OrderDao;
import edu.nwpu.store.dao.daoImp.OrderDaoImp;
import edu.nwpu.store.domain.Order;
import edu.nwpu.store.domain.OrderItem;
import edu.nwpu.store.domain.PageModel;
import edu.nwpu.store.domain.User;
import edu.nwpu.store.service.OrderService;
import edu.nwpu.store.utils.BeanFactory;
import edu.nwpu.store.utils.JDBCUtils;

public class OrderServiceImp implements OrderService {
	OrderDao dao = (OrderDao) BeanFactory.creatObject("OrderDao");

	@Override
	public void saveOrder(Order order) throws Exception {
		// TODO 保存订单和订单下所有的订单项，同时成功或同时失败（事务）
//		System.out.println("OrderServiceImp");
		/*
		 * try { JDBCUtils.startTransaction();// 开启事务 OrderDao dao = new OrderDaoImp();
		 * dao.saveOrder(order); for (OrderItem item : order.getList()) {
		 * dao.saveOrderItem(item); } JDBCUtils.commitAndClose(); } catch (Exception e)
		 * { // TODO: 发生问题就回滚 JDBCUtils.rollbackAndClose(); }
		 */
		Connection conn = null;
		try {
			// 获取连接
			conn = JDBCUtils.getConnection();
			// 开启事务
			conn.setAutoCommit(false);
			// 保存订单
			dao.saveOrder(conn, order);
			// 保存订单项
			for (OrderItem item : order.getList()) {
				dao.saveOrderItem(conn, item);
			}
			// 提交
			conn.commit();

			//System.out.println("end2");

		} catch (Exception e) {
			// 回滚
			conn.rollback();
			e.printStackTrace();
		} finally {
			if (null != conn) {
				conn.close();
				conn = null;
			}
		}
	}

	@Override
	public PageModel findMyOrdersWithPage(User user, int curNum) throws Exception {
		// 1_创建PageModel对象: 计算并且携带分页参数
		// SELECT COUNT(*) FORM orders WHERE uid=?: 当前用户的所有订单
		int totalRecords = dao.getTotalRecords(user);
		PageModel pm = new PageModel(curNum, 12, totalRecords);
		// 2_关联集合SELECT * FROM orders WHERE uid=? LIMIT ?,?
		List<Order> list = dao.findMyOrdersWithPage(user, pm.getStartIndex(), pm.getPageSize());
		pm.setList(list);
		// 3_关联一个url
		pm.setUrl("OrderServlet?method=findOrdersWithPage");
		return pm;
	}

	@Override
	public Order findOrderByOid(String oid) throws Exception {
		
		return dao.findOrderByOid(oid);
	}

	@Override
	public void updateOrder(Order order) throws Exception {
		dao.updateOrder(order);
	}

}
