package edu.nwpu.store.dao.daoImp;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import edu.nwpu.store.dao.UserDao;
import edu.nwpu.store.domain.User;
import edu.nwpu.store.utils.JDBCUtils;

public class UserDaoIml implements UserDao {

	@Override
	public void userRegister(User user) throws SQLException {
		// System.out.println(user.getSex());
		String sql = "INSERT INTO user"
				+ "(uid, username, password, name, email, telephone, birthday, sex, state, code)"
				+ " VALUES (?,?,?,?,?,?,?,?,?,?)";
		Object[] params = { user.getUid(), user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
				user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(), user.getCode() };
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		qr.update(sql, params);
	}

	@Override
	public User activeAccount(String code) throws SQLException {
		String sql = "SELECT * FROM user WHERE code=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		User user = qr.query(sql, new BeanHandler<User>(User.class), code);
		return user;
	}

	@Override
	public void updateUser(User user) throws SQLException {
		String sql = "UPDATE user SET username=?, password=?, name=?, email=?, telephone=?, birthday=?, sex=?, state=?, code=? WHERE uid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = { user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
				user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(), user.getCode(),
				user.getUid() };
		qr.update(sql, params);
	}

	@Override
	public User userLogin(User user) throws SQLException {
		String sql = "SELECT * FROM user WHERE username=? AND password=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		user = qr.query(sql, new BeanHandler<User>(User.class), user.getUsername(), user.getPassword());
		return user;
	}

}
