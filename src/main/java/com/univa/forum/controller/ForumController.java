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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.univa.forum.domain.ForumPost;
import com.univa.forum.domain.ForumSubjectBridge;
import com.univa.forum.dto.ForumPostDTO;
import com.univa.forum.dto.ForumPostDTO;
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
	
	/* 포럼 인덱스 */
	@GetMapping("")
	public String indexPage() {
		return "index";
	}
	
	/* 포럼 표지 */
	@GetMapping("/main")
	public String mainPage() {
		return "main/index";
	}
	
	/* 포럼 과목별 게시판 */
	@GetMapping("/main/board")
	public String ForumBoard(
			@RequestParam(value="min", defaultValue="0") int min,
			@RequestParam(value="max", defaultValue="10") int max,
			@RequestParam(value="subject") int[] subject,
			Model model) {
		
		return "test";
	}
	
	/* 포럼 게시물 */
	@GetMapping("/main/content")
	public String ForumContent(@RequestParam("id") int id, Model model) {
		ForumPost forum = forumService.findOneForumPost(id);
//		for(ForumPost cf : forum.getChildren()) {
//			// TODO 지우기
//			//System.out.println("child title : "+cf.getTitle());
//		}
//		System.out.println("parent title : "+forum.getParent().getTitle());
//		for(ForumSubjectBridge su : forum.getSubjects()) {
//			System.out.println("subs : "+ su.getSubject().getName());
//		}
		return "test";
	}
	
	/* 글쓰기 페이지 */
	@GetMapping("/main/write")
	public String ForumWritePage(Model model) {
		model.addAttribute("subject", forumService.findAllSubject());
		return "/main/write";
	}
	@PostMapping("/main/write")
	@ResponseBody
	public String ForumWritePost(
			ForumPostDTO forum,
			HttpSession session) {
		// TODO 서비스, 레포지토리 만들기
		ForumUserDTO user = (ForumUserDTO)session.getAttribute("ForumUserSession");
		if(user == null) {
			return "session error";
		}
		forum.setUser_idx(user.getIdx()); // 게시자
		forum.setType(0); // 타입
		forum.setState(0); // 상태
		
		return "ok";
	}
	
	/* 회원 가입 */
	@GetMapping("/signup")
	public String ForumSignupPage() {
		return "/account/signup";
	}
	@PostMapping("/signup")
	@ResponseBody
	public String ForumSignupPost(
			ForumUserDTO forumUser,
			Model model) {
		return forumService.userSignup(forumUser);
	}
	
	/* 로그인 */
	@GetMapping("/signin")
	public String ForumSigninPage() {
		return "/account/signin";
	}
	@PostMapping("/signin")
	@ResponseBody
	public String ForumSigninPost(
			ForumUserDTO forumUser,
			Model model,
			HttpSession session) {
		Optional<ForumUserDTO> user = forumService.userSignin(forumUser);
		if(user.isPresent()) {
			session.setAttribute("ForumUserSession", user.get());
			return "ok";
		} else {
			return "error";
		}
	}
	
	/* 로그아웃 */
	@GetMapping("/signout")
	public String ForumSignoutPost(Model model, HttpSession session) {
		session.removeAttribute("ForumUserSession");
		// TODO 로그아웃 후처리
		return "test";
	}
	
	/* 마이 페이지 */
	@GetMapping("/mypage")
	public String ForumMypageMain() {
		return "/mypage/index";
	}
	
	/* 마이 페이지 */
	@GetMapping("/mypage/profile")
	public String ForumMypagePage(Model model, HttpSession session) {
		return "/mypage/profile";
	}
	
	/* 나의 포럼 */
	@GetMapping("/mypage/forum")
	public String ForumMyforumPage() {
		return "/mypage/forum";
	}
	
	/* 정보수정 페이지 비밀번호 */
	@GetMapping("/mypage/editinfo_password")
	public String ForumEditinfoPasswordPage() {
		return "/mypage/password";
	}
	@PostMapping("/mypage/editinfo_password")
	@ResponseBody
	public String ForumEditinfoPasswordPost(@RequestParam(value="password") String password, HttpSession session) {
		ForumUserDTO user = (ForumUserDTO)session.getAttribute("ForumUserSession");
		if(forumService.validateUserPassword(user, password)) {
			user.setPass(true);
			session.setAttribute("ForumUserSession", user);
			return "ok";
		} else {
			return "error";
		}
	}
	
	/* 정보수정 페이지 */
	@GetMapping("/mypage/editinfo")
	public String ForumEditinfoPage(HttpSession session) {
		ForumUserDTO user = (ForumUserDTO)session.getAttribute("ForumUserSession");
		if(!user.getPass()) {
			return "redirect:/mypage/editinfo_password";
		}
		return "/mypage/edit_profile";
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
