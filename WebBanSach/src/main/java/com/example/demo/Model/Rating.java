package com.example.demo.Model;

public class Rating {
	private int rid, fromUser, toBook, star;

	public Rating(int rid, int fromUser, int toBook, int star) {
		this.rid = rid;
		this.fromUser = fromUser;
		this.toBook = toBook;
		this.star = star;
	}

	public Rating() {
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public int getFromUser() {
		return fromUser;
	}

	public void setFromUser(int fromUser) {
		this.fromUser = fromUser;
	}

	public int getToBook() {
		return toBook;
	}

	public void setToBook(int toBook) {
		this.toBook = toBook;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

	@Override
	public String toString() {
		return "Rating [rid=" + rid + ", fromUser=" + fromUser + ", toBook=" + toBook + ", star=" + star + "]";
	}

}
