package edu.nwpu.store.dao;

import java.sql.SQLException;

import edu.nwpu.store.domain.User;

public interface UserDao {

	public void userRegister(User user) throws SQLException;

	public User activeAccount(String code) throws SQLException;

	public void updateUser(User user) throws SQLException;

	public User userLogin(User user) throws SQLException;

	public User findUserbyUserName(User user) throws SQLException;

}
