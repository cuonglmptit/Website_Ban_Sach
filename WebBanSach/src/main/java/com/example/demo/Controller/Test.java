package com.example.demo.Controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DAO.BookDAO;
import com.example.demo.DAO.CategoryDAO;
import com.example.demo.Model.Book;

@RestController
public class Test {
	@GetMapping("/booksrest")
	public List<Book> getBooks(Model model) {
		CategoryDAO categoryDAO = new CategoryDAO();
		BookDAO bookDAO = new BookDAO();
		List<Book> books = bookDAO.selectAllBooks(); 
		model.addAttribute("books", books);
		model.addAttribute("categories", categoryDAO.selectAllCategories());
		return books;
	}
}
