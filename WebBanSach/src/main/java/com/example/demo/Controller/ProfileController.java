package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.DAO.UserDAO;
import com.example.demo.Model.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {
	@GetMapping("/profile")
	public String getProfile(Model model, HttpSession session, RedirectAttributes redirect) {
		User user = (User) session.getAttribute("user");
		try {
			if (user == null) {
				redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
				return "redirect:/signin";
			}
			UserDAO udao = new UserDAO();
			user = udao.selectUserByID(user.getUid());
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("user", user);
		return "profile";
	}
	@PutMapping("/profile/update")
	public String updateProfile(Model model, HttpSession session, User newuser, RedirectAttributes redirect) {
		User loggedUser = (User) session.getAttribute("user");
		try {
			if (loggedUser == null) {
				redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
				return "redirect:/signin";
			}
			UserDAO udao = new UserDAO();
			User user = udao.selectUserByID(loggedUser.getUid());
			
			user.setEmail(newuser.getEmail());
			user.setName(newuser.getName());
			user.setPhone(newuser.getPhone());
			user.setAddress(newuser.getAddress());
			udao.updateUser(user);
			session.setAttribute("user", user);
			model.addAttribute("user", user);
			redirect.addFlashAttribute("capNhat", "Cập nhật thành công!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/profile";
	}
}
