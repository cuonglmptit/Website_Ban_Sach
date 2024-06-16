package com.example.demo.Model;

import java.sql.Timestamp;

public class Transaction implements Comparable<Transaction>{
	private int transid, buyerid, bookbid, quantity;
	private String deliveryaddress, buyername, buyerphone, buyeremail;
	private Timestamp created;
	private String note;
	private int status;
	public Transaction(int transid, int buyerid, int bookbid, int quantity, String deliveryaddress, String buyername,
			String buyerphone, String buyeremail, Timestamp created, String note, int status) {
		this.transid = transid;
		this.buyerid = buyerid;
		this.bookbid = bookbid;
		this.quantity = quantity;
		this.deliveryaddress = deliveryaddress;
		this.buyername = buyername;
		this.buyerphone = buyerphone;
		this.buyeremail = buyeremail;
		this.created = created;
		this.note = note;
		this.status = status;
	}
	public Transaction() {
	}
	public int getTransid() {
		return transid;
	}
	public void setTransid(int transid) {
		this.transid = transid;
	}
	public int getBuyerid() {
		return buyerid;
	}
	public void setBuyerid(int buyerid) {
		this.buyerid = buyerid;
	}
	public int getBookbid() {
		return bookbid;
	}
	public void setBookbid(int bookbid) {
		this.bookbid = bookbid;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getDeliveryaddress() {
		return deliveryaddress;
	}
	public void setDeliveryaddress(String deliveryaddress) {
		this.deliveryaddress = deliveryaddress;
	}
	public String getBuyername() {
		return buyername;
	}
	public void setBuyername(String buyername) {
		this.buyername = buyername;
	}
	public String getBuyerphone() {
		return buyerphone;
	}
	public void setBuyerphone(String buyerphone) {
		this.buyerphone = buyerphone;
	}
	public String getBuyeremail() {
		return buyeremail;
	}
	public void setBuyeremail(String buyeremail) {
		this.buyeremail = buyeremail;
	}
	public Timestamp getCreated() {
		return created;
	}
	public void setCreated(Timestamp created) {
		this.created = created;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Transaction [transid=" + transid + ", buyerid=" + buyerid + ", bookbid=" + bookbid + ", quantity="
				+ quantity + ", deliveryaddress=" + deliveryaddress + ", buyername=" + buyername + ", buyerphone="
				+ buyerphone + ", buyeremail=" + buyeremail + ", created=" + created + ", note=" + note + ", status="
				+ status + "]";
	}
	@Override
	public int compareTo(Transaction o) {
		return this.created.compareTo(o.created);
	}
	
	
}
