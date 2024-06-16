package com.example.demo.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.DAO.BookDAO;
import com.example.demo.DAO.CategoryDAO;
import com.example.demo.DAO.CommentDAO;
import com.example.demo.DAO.RatingDAO;
import com.example.demo.DAO.TransactionDAO;
import com.example.demo.Model.Book;
import com.example.demo.Model.Comment;
import com.example.demo.Model.Rating;
import com.example.demo.Model.Transaction;
import com.example.demo.Model.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class BookController {
	
	
	@GetMapping("/search")
	public String getBooks(Model model, @RequestParam String search, HttpSession session) {
		CategoryDAO categoryDAO = new CategoryDAO();
		BookDAO bookDAO = new BookDAO();
		List<Book> books = bookDAO.selectBooksByKeyword(search);
		try {
			User user = (User) session.getAttribute("user");
			model.addAttribute("books", books);
			model.addAttribute("categories", categoryDAO.selectAllCategories());
			if(user != null) {
				if(user.getIsAdmin() == 1) {
					return "adminbooks";
				}
			}		
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "userbooks";
	}
	@GetMapping("/books")
	public String getBooks(Model model, HttpSession session) {
		CategoryDAO categoryDAO = new CategoryDAO();
		BookDAO bookDAO = new BookDAO();
		List<Book> books = bookDAO.selectAllBooks();
		try {
			User user = (User) session.getAttribute("user");
			model.addAttribute("books", books);
			model.addAttribute("categories", categoryDAO.selectAllCategories());
			if(user != null) {
				if(user.getIsAdmin() == 1) {
					return "adminbooks";
				}
			}		
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "userbooks";
	}
	@GetMapping("/book/{bid}")
	public String getBook(Model model, @PathVariable String bid, HttpSession session) {
		try {
			User user = (User) session.getAttribute("user");
			model.addAttribute("bid", bid);
			BookDAO bookDAO = new BookDAO();
			Book book = bookDAO.selectBookByBID(Integer.valueOf(bid)); 
			CategoryDAO categoryDAO = new CategoryDAO();
			CommentDAO cmtDao = new CommentDAO();
			List<Comment> comments = cmtDao.selectAllCommmentsOfBookByBID(Integer.valueOf(bid));
			model.addAttribute("categories", categoryDAO.selectAllCategories());
			model.addAttribute("book", book);
			model.addAttribute("comments", comments);
			int bought = 0;
			int rated = 0;
			RatingDAO ratingDAO = new RatingDAO();
			Rating userrate = null;
			if(user != null) {
				if(user.getIsAdmin() == 1) {
					return "admin_bookdetail";
				}else if(user.getIsAdmin() == 0) {
					if(book.getBid() <= 0) return "error";
					TransactionDAO transDAO = new TransactionDAO();
					if(!transDAO.selectUserBoughtBook(user.getUid(), book.getBid()).isEmpty()) {
						bought = 1;
					}
					
					userrate = ratingDAO.selectRatingByUIDAndBID(user.getUid(), Integer.valueOf(bid));
					if(userrate == null) {
						userrate = new Rating();
						rated = 0;
					}else {
						rated = 1;
					}
				}
			}
			List<Rating> allRateOfBook = ratingDAO.selectListRatingByBID(Integer.valueOf(bid));
			double overallrating = 0;
			for (Rating rating : allRateOfBook) {
				overallrating += rating.getStar();
			}
					
			overallrating = ((overallrating/allRateOfBook.size()));
			System.out.println(overallrating);
			model.addAttribute("usersrated", allRateOfBook.size());
			model.addAttribute("overallrating", overallrating);
			model.addAttribute("bought", bought);
			model.addAttribute("rated", rated);
			model.addAttribute("userrate", userrate);
			return "user_bookdetail";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
	}
	
	@PostMapping("/book/save/{bid}")
	public String addBook(Model model,HttpSession session ,@PathVariable String bid, Book book, RedirectAttributes redirect,
			@RequestParam("uploadFile") MultipartFile img) throws IOException {
		
		try {
			User user = (User) session.getAttribute("user");
			if(user != null) {
				if(user.getIsAdmin() == 1) {
					BookDAO bookDAO = new BookDAO();
					if(bookDAO.checkDuplicatedBook(book) > 0) {
						redirect.addFlashAttribute("thongBaoLoi", "Sách muốn thêm đã bị trùng với sách có trong CSDL (cùng tiêu đề, cùng tác giả)!");
						return "redirect:/book/"+bid;
					}
					
					if (!img.isEmpty() && img != null) {
			//			String uid = user.getUid()+"_";
						String originalFilename = img.getOriginalFilename();
						String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			//			String newFilename = uid + System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + extension;
						String newFilename = System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + extension;
						Path path = Paths.get("src/main/resources/static/uploads/");
			            InputStream inputStream = img.getInputStream();
			            Files.copy(inputStream, path.resolve(newFilename), StandardCopyOption.REPLACE_EXISTING);
//			            System.out.println(newFilename);
			            inputStream.close();
			            book.setCoverurl("/uploads/"+newFilename);
					}
					System.out.println("post: "+book);
					bookDAO.addBook(book);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "redirect:/books";
	}
	
	@PutMapping("/book/save/{bid}")
	public String updateBook(Model model,HttpSession session, @PathVariable String bid, Book book, RedirectAttributes redirect,
			@RequestParam("uploadFile") MultipartFile img) throws IOException {
		try {
			User user = (User) session.getAttribute("user");
			if(user != null) {
				if(user.getIsAdmin() == 1) {
					BookDAO bookDAO = new BookDAO();
					if(bookDAO.checkDuplicatedBook(book) > 0) {
						redirect.addFlashAttribute("thongBaoLoi", "Sách muốn cập nhật đã bị trùng với sách có trong CSDL (cùng tiêu đề, cùng tác giả)!");
						return "redirect:/book/"+bid;
					}
					if (!img.isEmpty() && img != null) {
			//			String uid = user.getUid()+"_";
						String originalFilename = img.getOriginalFilename();
						String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
			//			String newFilename = uid + System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + extension;
						String newFilename = System.currentTimeMillis() + "_" + UUID.randomUUID().toString() + extension;
						Path path = Paths.get("src/main/resources/static/uploads/");
			            InputStream inputStream = img.getInputStream();
			            Files.copy(inputStream, path.resolve(newFilename), StandardCopyOption.REPLACE_EXISTING);
		//	            System.out.println(newFilename);
			            inputStream.close();
			            book.setCoverurl("/uploads/"+newFilename);
					}
					System.out.println("put: "+book);
					bookDAO.updateBook(book);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "redirect:/books";
	}
	
	@DeleteMapping("/book/delete/{bid}")
	public String deleteBook(@PathVariable String bid) {
		BookDAO bookDAO = new BookDAO();
		bookDAO.deleteBookByBID(Integer.valueOf(bid));
		return "redirect:/books";
	}
	
	@GetMapping("/testupload")
	public String testUpload(){
		return "testupload";
	}
}
