package edu.nwpu.store.service;

import edu.nwpu.store.domain.Order;
import edu.nwpu.store.domain.PageModel;
import edu.nwpu.store.domain.User;

public interface OrderService {

	public void saveOrder(Order order) throws Exception;

	public PageModel findMyOrdersWithPage(User user, int curNum) throws Exception;

	public Order findOrderByOid(String oid) throws Exception;

}
