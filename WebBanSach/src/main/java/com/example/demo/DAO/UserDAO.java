package com.example.demo.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.demo.Model.User;

public class UserDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/jdbc_btl_ltw";
	private String jdbcUsername = "root";
	private String jdbcPasword = "Cuonglc123";
	
	private static final String INSERT_USER_SQL = "INSERT INTO user (`username`, `password`, `email`, `name`, `phone`, `address`, `isadmin`) VALUES (?, ?, ?, ?, ?, ?, ?);";
	private static final String UPDATE_USER_SQL = "UPDATE user SET `email` = ?, `name` = ?, `phone` = ?, `address` = ? WHERE (`uid` = ?);";
	private static final String VALID_USER = "SELECT * FROM user WHERE username=? and password=?;";
	private static final String SELECT_USER_BY_USERNAME = "SELECT * FROM user WHERE username = ?;";
	private static final String SELECT_USER_BY_ID = "SELECT * FROM user WHERE uid = ?;";
	
	public UserDAO() {
		
	}

	protected Connection getConnection() throws ClassNotFoundException {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPasword);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public int updateUser(User user) {
		int result = 0;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_USER_SQL)) {
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getName());
			ps.setString(3, user.getPhone());
			ps.setString(4, user.getAddress());
			ps.setInt(5, user.getUid());
			result = ps.executeUpdate();
			ps.close();
			conn.close();;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public int addUser(User user) {
		int result = 0;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_USER_SQL)) {
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getName());
			ps.setString(5, user.getPhone());
			ps.setString(6, user.getAddress());
			ps.setInt(7, user.getIsAdmin());
			result = ps.executeUpdate();
			ps.close();
			conn.close();;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int checkValidUser(String username, String password) {
		int valid = 0;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(VALID_USER)){
			ps.setString(1, username.toLowerCase());
			ps.setString(2, password);
//			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				valid = 1;
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valid;
	}
	
	public User selectUserByUsername(String username){
		User user = null;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_USER_BY_USERNAME)){
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				user = new User();
				user.setUid(rs.getInt("uid"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));;
				user.setPhone(rs.getString("phone"));
				user.setAddress(rs.getString("address"));
				user.setIsAdmin(rs.getInt("isadmin"));
			}
			conn.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public User selectUserByID(int uid){
		User user = null;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_USER_BY_ID)){
			ps.setInt(1, uid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				user = new User();
				user.setUid(rs.getInt("uid"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setName(rs.getString("name"));;
				user.setPhone(rs.getString("phone"));
				user.setAddress(rs.getString("address"));
				user.setIsAdmin(rs.getInt("isadmin"));
			}
			conn.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
}
