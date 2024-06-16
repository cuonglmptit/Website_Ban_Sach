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

import com.example.demo.Model.Book;
import com.example.demo.Model.Rating;

public class RatingDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/jdbc_btl_ltw";
	private String jdbcUsername = "root";
	private String jdbcPasword = "Cuonglc123";

	private static final String INSERT_RATING_SQL = "INSERT INTO rating (`fromuser`, `tobook`, `star`) VALUES (?, ?, ?);";
	private static final String SELECT_RATING_BY_UID_AND_BOOK = "SELECT * FROM rating WHERE fromuser = ? AND tobook = ?;";
	private static final String SELECT_RATING_OF_BOOK = "SELECT * FROM rating WHERE tobook = ?;";
	private static final String UPDATE_RATING_SQL = "UPDATE rating SET `fromuser` = ?, `tobook` = ?, `star` = ? WHERE (`rid` = ?)";

	public RatingDAO() {
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

	public int addRating(Rating rate) {
		int result = 0;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_RATING_SQL)) {
			ps.setInt(1, rate.getFromUser());
			ps.setInt(2, rate.getToBook());
			ps.setInt(3, rate.getStar());
			result = ps.executeUpdate();
			ps.close();
			conn.close();
			;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public int updateRating(Rating rate) {
		int result = 0;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_RATING_SQL)) {
			ps.setInt(1, rate.getFromUser());
			ps.setInt(2, rate.getToBook());
			ps.setInt(3, rate.getStar());
			ps.setInt(4, rate.getRid());
			result = ps.executeUpdate();
			ps.close();
			conn.close();
			;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Rating selectRatingByUIDAndBID(int uid, int bookid) {
		Rating rate = null;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_RATING_BY_UID_AND_BOOK)){
			ps.setInt(1, uid);
			ps.setInt(2, bookid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int rateid = rs.getInt("rid");
				int fromuser = rs.getInt("fromuser");
				int tobook = rs.getInt("tobook");
				int star = rs.getInt("star");
				rate = new Rating(rateid, fromuser, tobook, star);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rate;
	}
	public List<Rating> selectListRatingByBID(int bookid) {
		List<Rating> listRating = new ArrayList<>();
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_RATING_OF_BOOK)){
			ps.setInt(1, bookid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int rateid = rs.getInt("rid");
				int fromuser = rs.getInt("fromuser");
				int tobook = rs.getInt("tobook");
				int star = rs.getInt("star");
				listRating.add(new Rating(rateid, fromuser, tobook, star));
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listRating;
	}
	
}
