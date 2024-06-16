package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.DAO.RatingDAO;
import com.example.demo.Model.Rating;
import com.example.demo.Model.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class RatingController {
	@PostMapping("/rating/book/{bid}")
	public String postRating(@PathVariable String bid, @RequestParam("stars") int stars, HttpSession session, RedirectAttributes redirect) {
		User loggedUser = (User) session.getAttribute("user");
		try {
			if (loggedUser == null) {
				redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
				return "redirect:/signin";
			}
			
			RatingDAO ratingDAO = new RatingDAO();
			Rating rate = new Rating();
			rate.setFromUser(loggedUser.getUid());
			rate.setToBook(Integer.valueOf(bid));
			rate.setStar(stars);
			ratingDAO.addRating(rate);
		}catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "redirect:/book/"+bid;
	}
	@PutMapping("/rating/book/{bid}")
	public String updateRating(@PathVariable String bid, @RequestParam("stars") int stars, HttpSession session, RedirectAttributes redirect) {
		User loggedUser = (User) session.getAttribute("user");
		try {
			if (loggedUser == null) {
				redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
				return "redirect:/signin";
			}
			
			RatingDAO ratingDAO = new RatingDAO();
			Rating rate = ratingDAO.selectRatingByUIDAndBID(loggedUser.getUid(), Integer.valueOf(bid));
			rate.setStar(stars);
			ratingDAO.updateRating(rate);
		}catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "redirect:/book/"+bid;
	}
}
