package com.univa.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/forum")
public class ForumController {
	
	/* 포럼 표지 */
	@GetMapping("")
	public String mainPage() {
		return "test";
	}
	
	/* 포럼 과목별 게시판 */
	@GetMapping("/board")
	public String ForumBoard(
			@RequestParam(value="min", defaultValue="0") int min,
			@RequestParam(value="max", defaultValue="10") int max,
			@RequestParam(value="subject") int[] subject,
			Model model) {
		
		return "test";
	}
	
	/* 포럼 게시물 */
	@GetMapping("/content")
	public String ForumContent(@RequestParam("id") int id, Model model) {
		return "test";
	}
	
	/* 글쓰기 페이지 */
	@GetMapping("/write")
	public String ForumWritePagw() {
		return "test";
	}
	
	/* 회원 가입 */
	@GetMapping("/signup")
	public String ForumSignupPage() {
		return "test";
	}
	
	/* 로그인 */
	@GetMapping("/signin")
	public String ForumSigninPage() {
		return "test";
	}
	
	/* 마이 페이지 */
	@GetMapping("/mypage")
	public String ForumMypagePage() {
		return "test";
	}
	
	/* 나의 포럼 */
	@GetMapping("/mypage/forum")
	public String ForumMyforumPage() {
		return "test";
	}
	
	/* 정보수정 페이지 비밀번호 */
	@GetMapping("/mypage/editinfo_password")
	public String ForumEditinfoPasswordPage() {
		
		return "test";
	}
	
	/* 정보수정 페이지 */
	@GetMapping("/mypage/editinfo")
	public String ForumEditinfoPage() {
		
		return "test";
	}
	
	
}
