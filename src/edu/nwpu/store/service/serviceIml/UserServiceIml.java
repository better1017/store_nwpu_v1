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

	@Override
	public boolean activeAccount(String code) throws SQLException {
		// 实现激活功能
		UserDao dao = new UserDaoIml();

		// 对DB发送select * from user where code=?返回一个User对象
		User user = dao.activeAccount(code);
		if (null != user) {
			// 可以根据激活码查询到一个用户
			// 修改用户状态，清除激活码
			user.setState(1);
			user.setCode(null);

			// 对数据库执行一次真实的更新操作
			// UPDATE user SET username=?, password=?, name=?, email=?, telephone=?,
			// birthday=?, sex=?, state=?, code=? WHERE uid=?;
			dao.updateUser(user);
			return true;
		} else {
			// 不可以根据激活码查询到一个用户
			return false;
		}
	}

	@Override
	public User userLogin(User user) throws SQLException {
		// 此处：可以使用异常在模块之间传递数据
		UserDao dao = new UserDaoIml();
		// SELECT * FROM user WHERE username=? AND password=?
		user = dao.userLogin(user);
		if (null == user) {
			throw new RuntimeException("密码输入有误！");
		} else if (user.getState() == 0) {
			throw new RuntimeException("该用户尚未激活！");
		} else {
			return user;
		}
	}
}