package edu.nwpu.store.service;

import java.sql.SQLException;

import edu.nwpu.store.domain.User;

public interface UserService {

	// userRegister
	public void userRegister(User user) throws SQLException;

	/**
	 * @用户注册
	 * @author 杨远林
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	public boolean activeAccount(String code) throws SQLException;

	/**
	 * @用户登录
	 * @author 杨远林
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public User userLogin(User user) throws SQLException;

	/**
	 * @通过用户名查询用户
	 * @author 杨远林
	 * @param user
	 * @return
	 * @throws SQLException 
	 */
	public User checkUserName(User user) throws SQLException;

}
