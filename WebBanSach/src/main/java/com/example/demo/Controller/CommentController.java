package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.DAO.CommentDAO;
import com.example.demo.Model.Comment;
import com.example.demo.Model.User;

import jakarta.servlet.http.HttpSession;
@Controller
public class CommentController {
	@PostMapping("/comment/book/{bid}")
	public String postComment(@PathVariable String bid, @RequestParam String cmtContent, HttpSession session, RedirectAttributes redirect) {
		User loggedUser = (User) session.getAttribute("user");
		try {
			if (loggedUser == null) {
				redirect.addFlashAttribute("thongBao", "Bạn phải đăng nhập để thực hiện thao tác này!");
				return "redirect:/signin";
			}
			Comment cmt = new Comment();
			cmt.setFromuser(loggedUser.getUid());
			cmt.setTobook(Integer.valueOf(bid));
			cmt.setContent(cmtContent);
			CommentDAO cmtDao = new CommentDAO();
			cmtDao.addComment(cmt);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "redirect:/book/"+bid;
	}
}
