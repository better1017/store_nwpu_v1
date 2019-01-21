package edu.nwpu.store.dao;

import java.sql.SQLException;

import edu.nwpu.store.domain.User;

public interface UserDao {

	void userRegister(User user) throws SQLException;

}
