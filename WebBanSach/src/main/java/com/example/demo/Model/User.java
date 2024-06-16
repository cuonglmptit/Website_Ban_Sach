package com.example.demo.Model;

public class User {
	private int uid;
	private String username, password, email, name, phone, address;
	private int isAdmin;
	public User(int uid, String username, String password, String email, String name, String phone, String address,
			int isAdmin) {
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.isAdmin = isAdmin;
	}
	public User() {
		
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", password=" + password + ", email=" + email + ", name="
				+ name + ", phone=" + phone + ", address=" + address + ", isAdmin=" + isAdmin + "]";
	}
	
	
}
