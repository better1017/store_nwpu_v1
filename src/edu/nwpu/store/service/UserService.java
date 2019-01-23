package edu.nwpu.store.service;

import java.sql.SQLException;

import edu.nwpu.store.domain.User;

public interface UserService {

	// userRegister
	public void userRegister(User user) throws SQLException;

	public boolean activeAccount(String code) throws SQLException;

	public User userLogin(User user) throws SQLException;

}
