package com.example.demo.Controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.DAO.BookDAO;
import com.example.demo.DAO.TransactionDAO;
import com.example.demo.Model.Book;
import com.example.demo.Model.Transaction;
import com.example.demo.Model.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class TransactionController {
	@GetMapping("/buy/{bid}")
	public String getBuyTrans(@PathVariable String bid, Model model, HttpSession session, RedirectAttributes redirect) {
		User loggedUser = (User) session.getAttribute("user");
		try {
			if (loggedUser == null) {
				redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
				return "redirect:/signin";
			}
			BookDAO bookDAO = new BookDAO();
			Transaction trans = new Transaction();
			trans.setBuyerid(loggedUser.getUid());
			trans.setBookbid(Integer.valueOf(bid));
			trans.setDeliveryaddress(loggedUser.getAddress());
			trans.setBuyername(loggedUser.getName());
			trans.setBuyerphone(loggedUser.getPhone());
			trans.setBuyeremail(loggedUser.getEmail());
			
			model.addAttribute("book", bookDAO.selectBookByBID(Integer.valueOf(bid)));
			model.addAttribute("transaction", trans);
		}catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "transaction";
	}
	@PostMapping("/buy/{bid}")
	public String postTrans(@PathVariable String bid, Model model, HttpSession session, RedirectAttributes redirect,
			Transaction transaction) {
		User loggedUser = (User) session.getAttribute("user");
		try {
			if (loggedUser == null) {
				redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
				return "redirect:/signin";
			}
			transaction.setBookbid(Integer.valueOf(bid));
			transaction.setBuyerid(loggedUser.getUid());
			transaction.setCreated(new Timestamp(System.currentTimeMillis()));
			transaction.setStatus(1);
			TransactionDAO transactionDAO = new TransactionDAO();
			transactionDAO.addTrans(transaction);
			System.out.println(transaction);
		}catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "redirect:/yourpendingtrans";
	}

	@GetMapping("/transaction/{transid}")
	public String getCreatedTrans(Model model, HttpSession session, @PathVariable String transid
			, RedirectAttributes redirect) {
		User loggedUser = (User) session.getAttribute("user");
		System.out.println("get trans");
		try {
			if (loggedUser == null) {
				redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
				return "redirect:/signin";
			}
			TransactionDAO transDAO = new TransactionDAO();
			BookDAO bookDAO = new BookDAO();
			Transaction transaction = null;
			if(loggedUser.getIsAdmin() == 0) {
				 transaction = transDAO.selectTransByTransIDAndBuyerUID(Integer.valueOf(transid), loggedUser.getUid());
			}else if(loggedUser.getIsAdmin() == 1) {
				transaction = transDAO.selectTransByTransID(Integer.valueOf(transid));
			}
			if(transaction == null) {
				return "error";
			}
			model.addAttribute("transaction", transaction);
			model.addAttribute("book", bookDAO.selectBookByBID(transaction.getBookbid()));
		}catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "createdtransaction";
	}
	
	@PutMapping("/transaction/decline/{transid}")
	public String declineTrans(Model model,@PathVariable String transid, HttpSession session, RedirectAttributes redirect) {
		User loggedUser = (User) session.getAttribute("user");
		System.out.println("decline trans");
		try {
			if (loggedUser == null) {
				redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
				return "redirect:/signin";
			}
			TransactionDAO transDAO = new TransactionDAO();
			Transaction transaction = null;
			if(loggedUser.getIsAdmin() == 0) {
				 transaction = transDAO.selectTransByTransIDAndBuyerUID(Integer.valueOf(transid), loggedUser.getUid());
				 if(transaction == null) {
					 return "error";
				 }
				 transaction.setStatus(3);//1=pending, 2=success, 3=failed
				 transDAO.updateTransStatus(transaction);
			}else if(loggedUser.getIsAdmin() == 1) {
				transaction = transDAO.selectTransByTransID(Integer.valueOf(transid));
				if(transaction == null) {
					 return "error";
				 }
				transaction.setStatus(3);//1=pending, 2=success, 3=failed
				transDAO.updateTransStatus(transaction);
			}

		}catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "redirect:/yourexpiredtrans";
	}
	@PutMapping("/transaction/confirm/{transid}")
	public String confirmTrans(Model model,@PathVariable String transid, HttpSession session, RedirectAttributes redirect) {
		User loggedUser = (User) session.getAttribute("user");
		System.out.println("confirm trans");
		try {
			if (loggedUser == null) {
				redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
				return "redirect:/signin";
			}
			TransactionDAO transDAO = new TransactionDAO();
			Transaction transaction = null;
			if(loggedUser.getIsAdmin() == 1) {
				transaction = transDAO.selectTransByTransID(Integer.valueOf(transid));
				if(transaction == null) {
					return "error";
				}
				transaction.setStatus(2);//1=pending, 2=success, 3=failed
				transDAO.updateTransStatus(transaction);
				BookDAO bookDAO = new BookDAO();
				Book bookSoldUpdate = bookDAO.selectBookByBID(transaction.getBookbid());
//				System.out.println(transaction);
				bookSoldUpdate.setSold(bookSoldUpdate.getSold() + transaction.getQuantity());
				bookDAO.updateBook(bookSoldUpdate);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "redirect:/yourexpiredtrans";
	}
	
	@GetMapping("/yourpendingtrans")
	public String getListCreatedPendingTrans(Model model, HttpSession session
			, RedirectAttributes redirect) {
		User loggedUser = (User) session.getAttribute("user");
		System.out.println("get list pending trans");
		try {
			if (loggedUser == null) {
				redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
				return "redirect:/signin";
			}
			TransactionDAO transDAO = new TransactionDAO();
			List<Transaction> transs = null;
			if(loggedUser.getIsAdmin() == 0) {
				 transs = transDAO.selectTransByBuyerUIDAndStatus(loggedUser.getUid(), 1);
			}else if(loggedUser.getIsAdmin() == 1) {
				transs = transDAO.selectAllTransByStatus(1);
			}
			model.addAttribute("transactions", transs);
		}catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "transs";
	}
	@GetMapping("/yourexpiredtrans")
	public String getListCreatedExpiredTrans(Model model, HttpSession session
			, RedirectAttributes redirect) {
		User loggedUser = (User) session.getAttribute("user");
		System.out.println("get list expired trans");
		try {
			if (loggedUser == null) {
				redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
				return "redirect:/signin";
			}
			TransactionDAO transDAO = new TransactionDAO();
			List<Transaction> transs = new ArrayList<>();
			if(loggedUser.getIsAdmin() == 0) {
				transs.addAll(transDAO.selectTransByBuyerUIDAndStatus(loggedUser.getUid(), 2));
				transs.addAll(transDAO.selectTransByBuyerUIDAndStatus(loggedUser.getUid(), 3));
			}else if(loggedUser.getIsAdmin() == 1) {
				transs.addAll(transDAO.selectAllTransByStatus(2));
				transs.addAll(transDAO.selectAllTransByStatus(3));
			}
			Collections.sort(transs, Comparator.reverseOrder());
			model.addAttribute("transactions", transs);
		}catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "transs";
	}
	

	
}
