package com.example.demo.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.Model.Transaction;

public class TransactionDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/jdbc_btl_ltw";
	private String jdbcUsername = "root";
	private String jdbcPasword = "Cuonglc123";
	
	private static final String SELECT_ALL_TRANS_STATUS = "SELECT * FROM transaction WHERE status = ? ORDER BY created DESC;";
	private static final String SELECT_ALL_TRANS_BY_BUYERUID_AND_STATUS = "SELECT * FROM transaction WHERE buyeruid = ? AND status = ? ORDER BY created DESC;";
	private static final String SELECT_ALL_SUSSCESS_TRANS_BY_BOOKID_AND_USERID = "SELECT * FROM transaction WHERE buyeruid = ? AND bookbid = ? AND status = 2 ORDER BY created DESC;";
	private static final String UPDATE_TRANS_STATUS_SQL = "UPDATE transaction SET `status` = ? WHERE (`transid` = ?);";
	private static final String SELECT_TRANS_BY_TRANSID = "SELECT * FROM transaction WHERE transid = ? ORDER BY created DESC;";
	private static final String SELECT_TRANS_BY_TRANSID_AND_BUYERUID = "SELECT * FROM transaction WHERE transid = ? AND buyeruid = ?;";
	private static final String INSERT_TRANS_SQL = "INSERT INTO transaction (`buyeruid`, `bookbid`, `quantity`, `deliveryaddress`, `buyername`,"
			+ " `buyerphone`, `buyeremail`, `created`, `note`, `status`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	
	public TransactionDAO() {
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

	public List<Transaction> selectAllTransByStatus(int status) {
		List<Transaction> transs = new ArrayList<>();
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_TRANS_STATUS)){
			ps.setInt(1, status);
//			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int transid = rs.getInt("transid");
				int buyeruid = rs.getInt("buyeruid");
				int bookbid = rs.getInt("bookbid");
				int quantity = rs.getInt("quantity");
				String deliveryaddress = rs.getString("deliveryaddress");
				String buyername = rs.getString("buyername");
				String buyerphone = rs.getString("buyerphone");
				String buyeremail = rs.getString("buyeremail");
				Timestamp created = rs.getTimestamp("created");
				String note = rs.getString("note");
				int stt = rs.getInt("status");
				transs.add(new Transaction(transid, buyeruid, bookbid, quantity, deliveryaddress, buyername, buyerphone, buyeremail, created, note, stt));
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transs;
	}
	public List<Transaction> selectUserBoughtBook(int uid, int bookid) {
		List<Transaction> transs = new ArrayList<>();
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SUSSCESS_TRANS_BY_BOOKID_AND_USERID)){
			ps.setInt(1, uid);
			ps.setInt(2, bookid);
//			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int transid = rs.getInt("transid");
				int buyeruid = rs.getInt("buyeruid");
				int bookbid = rs.getInt("bookbid");
				int quantity = rs.getInt("quantity");
				String deliveryaddress = rs.getString("deliveryaddress");
				String buyername = rs.getString("buyername");
				String buyerphone = rs.getString("buyerphone");
				String buyeremail = rs.getString("buyeremail");
				Timestamp created = rs.getTimestamp("created");
				String note = rs.getString("note");
				int stt = rs.getInt("status");
				transs.add(new Transaction(transid, buyeruid, bookbid, quantity, deliveryaddress, buyername, buyerphone, buyeremail, created, note, stt));
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transs;
	}
	public List<Transaction> selectTransByBuyerUIDAndStatus(int buyerUid, int status) {
		List<Transaction> transs = new ArrayList<>();
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_TRANS_BY_BUYERUID_AND_STATUS)){
			ps.setInt(1, buyerUid);
			ps.setInt(2, status);
//			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int transid = rs.getInt("transid");
				int buyeruid = rs.getInt("buyeruid");
				int bookbid = rs.getInt("bookbid");
				int quantity = rs.getInt("quantity");
				String deliveryaddress = rs.getString("deliveryaddress");
				String buyername = rs.getString("buyername");
				String buyerphone = rs.getString("buyerphone");
				String buyeremail = rs.getString("buyeremail");
				Timestamp created = rs.getTimestamp("created");
				String note = rs.getString("note");
				int stt = rs.getInt("status");
				transs.add(new Transaction(transid, buyeruid, bookbid, quantity, deliveryaddress, buyername, buyerphone, buyeremail, created, note, stt));
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transs;
	}
	public Transaction selectTransByTransIDAndBuyerUID(int transactionid, int uid) {
		Transaction trans = null;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_TRANS_BY_TRANSID_AND_BUYERUID)){
			ps.setInt(1, transactionid);
			ps.setInt(2, uid);
//			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int transid = rs.getInt("transid");
				int buyeruid = rs.getInt("buyeruid");
				int bookbid = rs.getInt("bookbid");
				int quantity = rs.getInt("quantity");
				String deliveryaddress = rs.getString("deliveryaddress");
				String buyername = rs.getString("buyername");
				String buyerphone = rs.getString("buyerphone");
				String buyeremail = rs.getString("buyeremail");
				Timestamp created = rs.getTimestamp("created");
				String note = rs.getString("note");
				int stt = rs.getInt("status");
				trans = new Transaction(transid, buyeruid, bookbid, quantity, deliveryaddress, buyername, buyerphone, buyeremail, created, note, stt);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return trans;
	}
	public Transaction selectTransByTransID(int transactionid) {
		Transaction trans = null;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_TRANS_BY_TRANSID)){
			ps.setInt(1, transactionid);
//			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int transid = rs.getInt("transid");
				int buyeruid = rs.getInt("buyeruid");
				int bookbid = rs.getInt("bookbid");
				int quantity = rs.getInt("quantity");
				String deliveryaddress = rs.getString("deliveryaddress");
				String buyername = rs.getString("buyername");
				String buyerphone = rs.getString("buyerphone");
				String buyeremail = rs.getString("buyeremail");
				Timestamp created = rs.getTimestamp("created");
				String note = rs.getString("note");
				int stt = rs.getInt("status");
				trans = new Transaction(transid, buyeruid, bookbid, quantity, deliveryaddress, buyername, buyerphone, buyeremail, created, note, stt);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return trans;
	}
	
	public int addTrans(Transaction trans) {
		int result = 0;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_TRANS_SQL)) {
			ps.setInt(1, trans.getBuyerid());
			ps.setInt(2, trans.getBookbid());
			ps.setInt(3, trans.getQuantity());
			ps.setString(4, trans.getDeliveryaddress());
			ps.setString(5, trans.getBuyername());
			ps.setString(6, trans.getBuyerphone());
			ps.setString(7, trans.getBuyeremail());
			ps.setTimestamp(8, trans.getCreated());
			ps.setString(9, trans.getNote());
			ps.setInt(10, trans.getStatus());
			result = ps.executeUpdate();
			ps.close();
			conn.close();;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public int updateTransStatus(Transaction trans) {
		int result = 0;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_TRANS_STATUS_SQL)) {
			ps.setInt(1, trans.getStatus());
			ps.setInt(2, trans.getTransid());
			result = ps.executeUpdate();
			ps.close();
			conn.close();;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
