package edu.nwpu.store.dao.daoImp;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

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

}
