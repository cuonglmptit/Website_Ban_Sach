package com.example.demo.Model;

import java.sql.Date;

public class Book {
	private int bid;
	private String title, author;
	private int category;
	private Date rlsdate;
	private int pages, sold;
	private String descr, coverurl;
	public Book(int bid, String title, String author, int category, Date rlsdate, int pages, int sold, String descr,
			String coverurl) {
		this.bid = bid;
		this.title = title;
		this.author = author;
		this.category = category;
		this.rlsdate = rlsdate;
		this.pages = pages;
		this.sold = sold;
		this.descr = descr;
		this.coverurl = coverurl;
	}
	public Book() {

	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public Date getRlsdate() {
		return rlsdate;
	}
	public void setRlsdate(Date rlsdate) {
		this.rlsdate = rlsdate;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public int getSold() {
		return sold;
	}
	public void setSold(int sold) {
		this.sold = sold;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getCoverurl() {
		return coverurl;
	}
	public void setCoverurl(String coverurl) {
		this.coverurl = coverurl;
	}
	@Override
	public String toString() {
		return "Book [bid=" + bid + ", title=" + title + ", author=" + author + ", category=" + category + ", rlsdate="
				+ rlsdate + ", pages=" + pages + ", sold=" + sold + ", descr=" + descr + ", coverurl=" + coverurl + "]";
	}
	
	
}
