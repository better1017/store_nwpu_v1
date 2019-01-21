package edu.nwpu.store.service;

import java.sql.SQLException;

import edu.nwpu.store.domain.User;

public interface UserService {

	//userRegister
	public void userRegister(User user) throws SQLException;

}
