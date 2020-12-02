package com.univa.forum.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		// TODO 완료후 상태에 따른 리턴값?
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
	
	/* 로그아웃 */
	@GetMapping("/signout")
	public String ForumSignoutPost(Model model, HttpSession session) {
		session.removeAttribute("ForumUserSession");
		// TODO 로그아웃 후처리
		return "test";
	}
	
	/* 마이 페이지 */
	@GetMapping("/mypage/profile")
	public String ForumMypagePage(Model model, HttpSession session) {
		return "/mypage/profile";
	}
	
	/* 나의 포럼 */
	@GetMapping("/mypage/forum")
	public String ForumMyforumPage() {
		return "test";
	}
	
	/* 정보수정 페이지 비밀번호 */
	@GetMapping("/mypage/editinfo_password")
	public String ForumEditinfoPasswordPage() {
		return "/mypage/password";
	}
	@PostMapping("/mypage/editinfo_password")
	public String ForumEditinfoPasswordPost(@RequestParam(value="password") String password, HttpSession session) {
		ForumUserDTO user = (ForumUserDTO)session.getAttribute("ForumUserSession");
		if(forumService.validateUserPassword(user, password)) {
			return "redirect:/mypage/editinfo";
		} else {
			return 
		}
	}
	
	/* 정보수정 페이지 */
	@GetMapping("/mypage/editinfo")
	public String ForumEditinfoPage() {
		
		return "test";
	}
	
	/* 이미지 뷰어 */
	@GetMapping("/img")
	public ResponseEntity<Resource> imageView(@RequestParam("id") String img) throws IOException {
		Path path = Paths.get("uploads/imgs/" + img);
		String contentType = Files.probeContentType(path);

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, contentType);
		
		Resource resource = null;
		try {
			resource = new InputStreamResource(Files.newInputStream(path));
		} catch (Exception e) {
			e.getStackTrace();
			// 이미지가 없을 경우 발생
		}
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}
	
	
}
