package com.example.demo.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.Model.Book;

public class BookDAO {
	private String jdbcURL = "jdbc:mysql://localhost:3306/jdbc_btl_ltw";
	private String jdbcUsername = "root";
	private String jdbcPasword = "Cuonglc123";
	
	private static final String SELECT_ALL_BOOKS = "SELECT * FROM book;";
	private static final String SELECT_ALL_BOOKS_BY_KEYWORD = "SELECT * FROM book WHERE title LIKE ? OR author LIKE ?;";
	private static final String CHECK_DUPLICATED_BOOK = "SELECT * FROM book WHERE title = ? AND author = ? AND bid != ?;";
	private static final String SELECT_BOOK_BY_BID = "SELECT * FROM book WHERE bid = ?;";
	private static final String INSERT_BOOK_SQL = "INSERT INTO book (`title`, `author`, `category`, `rlsdate`, `"
			+ "pages`, `sold`, `descr`, `coverurl`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String UPDATE_BOOK_SQL = "UPDATE book SET `title` = ?, `author` = ?, `category` = ?, "
			+ "`rlsdate` = ?, `pages` = ?, `sold` = ?, `descr` = ?, `coverurl` = ? WHERE (`bid` = ?);";
	private static final String DELETE_BOOK_SQL = "DELETE FROM book WHERE (`bid` = ?);";
	
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
	
	public List<Book> selectAllBooks(){
		List<Book> books = new ArrayList<>();
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_BOOKS)){
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int bid = rs.getInt("bid");
				String title = rs.getString("title"), author = rs.getString("author");
				int category = rs.getInt("category");
				Date rlsdate = rs.getDate("rlsdate");
				int pages = rs.getInt("pages"), sold = rs.getInt("sold");
				String descr = rs.getString("descr"), coverurl = rs.getString("coverurl");
				Book book = new Book(bid, title, author, category, rlsdate, pages, sold, descr, coverurl);
				books.add(book);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}
	public List<Book> selectBooksByKeyword(String keyword){
		List<Book> books = new ArrayList<>();
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_ALL_BOOKS_BY_KEYWORD)){
			ps.setString(1, "%"+keyword+"%");
			ps.setString(2, "%"+keyword+"%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int bid = rs.getInt("bid");
				String title = rs.getString("title"), author = rs.getString("author");
				int category = rs.getInt("category");
				Date rlsdate = rs.getDate("rlsdate");
				int pages = rs.getInt("pages"), sold = rs.getInt("sold");
				String descr = rs.getString("descr"), coverurl = rs.getString("coverurl");
				Book book = new Book(bid, title, author, category, rlsdate, pages, sold, descr, coverurl);
				books.add(book);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return books;
	}
	
	public int addBook(Book book) {
		int result = 0;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(INSERT_BOOK_SQL)) {
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setInt(3, book.getCategory());
			ps.setDate(4, book.getRlsdate());
			ps.setInt(5, book.getPages());
			ps.setInt(6, book.getSold());
			ps.setString(7, book.getDescr());
			ps.setString(8, book.getCoverurl());
			result = ps.executeUpdate();
			ps.close();
			conn.close();;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public int checkDuplicatedBook(Book book) {
		int result = 0;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(CHECK_DUPLICATED_BOOK)) {
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setInt(3, book.getBid());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				result += 1;
			}
			ps.close();
			conn.close();;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateBook(Book book) {
		int result = 0;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(UPDATE_BOOK_SQL)) {
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setInt(3, book.getCategory());
			ps.setDate(4, book.getRlsdate());
			ps.setInt(5, book.getPages());
			ps.setInt(6, book.getSold());
			ps.setString(7, book.getDescr());
			ps.setString(8, book.getCoverurl());
			ps.setInt(9, book.getBid());
			result = ps.executeUpdate();
			ps.close();
			conn.close();;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteBookByBID(int book_id) {
		int result = 0;
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(DELETE_BOOK_SQL)) {
			ps.setInt(1, book_id);
			result = ps.executeUpdate();
			ps.close();
			conn.close();;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Book selectBookByBID(int book_id) {
		Book book = new Book();
		try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(SELECT_BOOK_BY_BID)){
			ps.setInt(1, book_id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int bid = rs.getInt("bid");
				String title = rs.getString("title"), author = rs.getString("author");
				int category = rs.getInt("category");
				Date rlsdate = rs.getDate("rlsdate");
				int pages = rs.getInt("pages"), sold = rs.getInt("sold");
				String descr = rs.getString("descr"), coverurl = rs.getString("coverurl");
				book = new Book(bid, title, author, category, rlsdate, pages, sold, descr, coverurl);
			}
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return book;
	}
}
