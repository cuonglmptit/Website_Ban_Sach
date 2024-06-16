package com.example.demo.Model;

import java.sql.Timestamp;

public class Comment {
	private int cmtid, fromuser, tobook;
	private String uname, content;
	private Timestamp time;
	public Comment() {

	}
	public Comment(int cmtid, int fromuser, int tobook, String content, Timestamp time) {
		this.cmtid = cmtid;
		this.fromuser = fromuser;
		this.tobook = tobook;
		this.content = content;
		this.time = time;
	}
	public int getCmtid() {
		return cmtid;
	}
	public void setCmtid(int cmtid) {
		this.cmtid = cmtid;
	}
	public int getFromuser() {
		return fromuser;
	}
	public void setFromuser(int fromuser) {
		this.fromuser = fromuser;
	}
	public int getTobook() {
		return tobook;
	}
	public void setTobook(int tobook) {
		this.tobook = tobook;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Comment [cmtid=" + cmtid + ", fromuser=" + fromuser + ", tobook=" + tobook + ", uname=" + uname
				+ ", content=" + content + ", time=" + time + "]";
	}

}
