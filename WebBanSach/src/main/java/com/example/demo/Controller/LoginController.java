package com.example.demo.Controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.DAO.UserDAO;
import com.example.demo.Model.RandomCUSTOM;
import com.example.demo.Model.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@GetMapping("/logout")
	public String getDangXuat(Model model, HttpSession session) {
		session.invalidate();
		return "redirect:/books";
	}
	
	@GetMapping("/signup")
	public String getSignUp(Model model, HttpSession session) {
		//Nếu đã đăng nhập thì chuyển hướng về trang chủ
		User loggedUser = (User) session.getAttribute("user");
		if(loggedUser != null) {
			return "redirect:/books";
		}
		User user = new User();
		user.setName("User mới "+ RandomCUSTOM.generateRandomCode());
		model.addAttribute("user", user);
		return "signup";
	}
	@PostMapping("/signup")
	public String postSignUp(Model model, User user, HttpSession session, RedirectAttributes redirect) {
//		System.out.println(user);
		User loggedUser = (User) session.getAttribute("user");
		if(loggedUser != null) {
			return "redirect:/books";
		}
		
		int success = 0;
	    try {
	        UserDAO udao = new UserDAO();
	        if(udao.selectUserByUsername(user.getUsername()) == null) {
	        	if(user.getName() == null) user.setName(user.getUsername());
	            success = udao.addUser(user); 
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    if(success == 0) {
	    	redirect.addFlashAttribute("thongBao", "Đăng ký không thành công! Tài khoản bị trùng!");
	        return "redirect:/signup"; // trả về trang đăng ký với thông báo lỗi
	    } else {
	    	redirect.addFlashAttribute("thongBao", "Đăng ký thành công!");
	        return "redirect:/signin"; // điều hướng đến trang đăng nhập với thông báo thành công
	    }
	}
	
	@GetMapping("/signin")
	public String getSignIn(Model model, HttpSession session) {
		User loggedUser = (User) session.getAttribute("user");
		if(loggedUser != null) {
			return "redirect:/books";
		}
		return "signin";
	}
	
	@PostMapping("/signin")
	public String postLogin(Model model, @RequestParam("username") String username,
	                        @RequestParam("password") String password,
	                        HttpSession session, RedirectAttributes redirect) {
		User loggedUser = (User) session.getAttribute("user");
		if(loggedUser != null) {
			return "redirect:/books";
		}
		
	    // Kiểm tra thông tin đăng nhập
		UserDAO udao = new UserDAO();
	    if (udao.checkValidUser(username, password) != 0) {
	        // Lưu người dùng vào session
	    	User user = udao.selectUserByUsername(username);
	    	session.setAttribute("user", user);
	        return "redirect:/books";
	    } else {
	    	redirect.addFlashAttribute("thongBao", "Sai tài khoản hoặc mật khẩu!");
	        return "redirect:/signin";
	    }
	}
	
}
