package com.example.demo.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.Model.Comment;


public class CommentDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/jdbc_btl_ltw";
	private String jdbcUsername = "root";
	private String jdbcPasword = "Cuonglc123";

	private static final String INSERT_CMT_SQL = "INSERT INTO comment (`fromuser`, `tobook`, `content`, `time`) VALUES (?, ?, ?, ?);";
	private static final String SELECT_ALL_CMT_OF_BOOK_SQL = "SELECT * FROM comment WHERE tobook = ?;";

	public CommentDAO() {
		// TODO Auto-generated constructor stub
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

	public int addComment(Comment comment) {
		int result = 0;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_CMT_SQL)) {
			ps.setInt(1, comment.getFromuser());
			ps.setInt(2, comment.getTobook());
			ps.setString(3, comment.getContent());
			ps.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			result = ps.executeUpdate();
			ps.close();
			conn.close();
			;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<Comment> selectAllCommmentsOfBookByBID(int bookid){
		List<Comment> comments = new ArrayList<>();
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_CMT_OF_BOOK_SQL)){
			ps.setInt(1, bookid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int cmtId = rs.getInt("cmtid");
				int fromUser = rs.getInt("fromuser");
				int toBook= rs.getInt("tobook");
				String content = rs.getString("content");
				Timestamp time = rs.getTimestamp("time");
				Comment cmt = new Comment(cmtId, fromUser, toBook, content, time);
				UserDAO userDAO = new UserDAO();
				cmt.setUname(userDAO.selectUserByID(fromUser).getName());
				comments.add(cmt);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return comments;
	}
}
