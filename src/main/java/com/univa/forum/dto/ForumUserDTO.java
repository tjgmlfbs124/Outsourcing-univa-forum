package com.univa.forum.dto;

import org.springframework.web.multipart.MultipartFile;

public class ForumUserDTO {
	private String username;
	private String password;
	private String nickname;
	private String email;
	private MultipartFile file;
	private int grade_idx;
	private String nation;
	private int state;
	
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public MultipartFile getImage_url() {
		return file;
	}
	public void setImage_url(MultipartFile file) {
		this.file = file;
	}
	public int getGrade_idx() {
		return grade_idx;
	}
	public void setGrade_idx(int grade_idx) {
		this.grade_idx = grade_idx;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
}
