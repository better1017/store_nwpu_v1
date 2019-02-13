package edu.nwpu.store.service;

import edu.nwpu.store.domain.Order;

public interface OrderService {

	public void saveOrder(Order order) throws Exception;

}
