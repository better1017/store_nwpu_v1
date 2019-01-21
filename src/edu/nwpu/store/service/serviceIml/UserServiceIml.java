package edu.nwpu.store.service.serviceIml;

import java.sql.SQLException;

import edu.nwpu.store.dao.UserDao;
import edu.nwpu.store.dao.daoImp.UserDaoIml;
import edu.nwpu.store.domain.User;
import edu.nwpu.store.service.UserService;

public class UserServiceIml implements UserService {

	@Override
	public void userRegister(User user) throws SQLException {
		// 实现注册功能
		UserDao dao = new UserDaoIml();
		dao.userRegister(user);
	}

}