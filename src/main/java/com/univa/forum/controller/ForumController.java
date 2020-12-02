package com.univa.forum.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.univa.forum.dto.ForumUserDTO;
import com.univa.forum.service.ForumService;

@Controller
@RequestMapping("/forum")
public class ForumController {
	private final ForumService forumService;
	
	@Autowired
	public ForumController(ForumService service) {
		this.forumService = service;
	}
	
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
	public String ForumWritePage() {
		return "test";
	}
	
	/* 회원 가입 */
	@GetMapping("/signup")
	public String ForumSignupPage() {
		return "/account/signup";
	}
	
	@PostMapping("/signup")
	@ResponseBody
	public String ForumSignupPost(
			@RequestBody ForumUserDTO forumUser,
			Model model) {
		forumService.userSignup(forumUser);
		// TODO
		return "test";
	}
	
	/* 로그인 */
	@GetMapping("/signin")
	public String ForumSigninPage() {
		return "/account/signin";
	}
	@PostMapping("/signin")
	@ResponseBody
	public String ForumSigninPost(
			@RequestBody ForumUserDTO forumUser,
			Model model,
			HttpSession session) {
		Optional<ForumUserDTO> user = forumService.userSignin(forumUser);
		if(user.isPresent()) {
			// TODO 로그인 성공
			session.setAttribute("ForumUserSession", user.get());
			System.out.println("로그인 성공!");
		} else {
			// TODO 로그인 실패
			System.out.println("로그인 실패!");
		}
		return "test";
	}
	
	@GetMapping("/signout")
	public String ForumSignoutPost(Model model, HttpSession session) {
		session.removeAttribute("ForumUserSession");
		// TODO 로그아웃 후처리
		return "test";
	}
	
	/* 마이 페이지 */
	@GetMapping("/mypage")
	public String ForumMypagePage(Model model, HttpSession session) {
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
