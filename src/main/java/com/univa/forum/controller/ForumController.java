package com.univa.forum.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
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
import com.univa.forum.domain.ForumRecommend;
import com.univa.forum.domain.ForumSubjectBridge;
import com.univa.forum.domain.ForumUserGrade;
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
	public String mainPage(Model model, HttpSession session) {
		ForumUserDTO user = (ForumUserDTO)session.getAttribute("ForumUserSession");
		List<ForumPost> posts;
		if(user != null) {
			posts = forumService.findHeaderForumOrderByChildCnt(user.getIdx());
		} else {
			posts = forumService.findHeaderForumOrderByChildCnt();
		}
		
		model.addAttribute("questions", posts);
		
		return "main/index";
	}
	
	/* 포럼 과목별 게시판 */
	@GetMapping("/main/board")
	public String ForumBoard(
			@RequestParam(value="min", defaultValue="0") int min,
			@RequestParam(value="max", defaultValue="10") int max,
			@RequestParam(value="subject", defaultValue="0") int[] subject,
			Model model,
			HttpSession session) {
		ForumUserDTO user = (ForumUserDTO)session.getAttribute("ForumUserSession");
		List<ForumPost> forums;
		if(subject[0] == 0) {
			forums = forumService.findHeaderForumList(0, 10, user.getIdx());
		} else {
			forums = forumService.findQuestionsBySubject(0, 10, subject, user.getIdx());
		}
		// TODO 게시판 결과 테스트 지우기
//		for (ForumPost post : forums) {
//			System.out.println("post :"+post.getTitle());
//			System.out.println("post reco? :"+post.getRecommended());
//			System.out.println("post recnt :"+post.getRecommendedCount());
//		}
		
		return "test";
	}
	
	/* 포럼 내용 보기 */
	@GetMapping("/main/content")
	@ResponseBody //TODO 테스트용 지우기
	public String ForumContent(@RequestParam("id") int id, Model model, HttpSession session) {
		ForumUserDTO user = (ForumUserDTO)session.getAttribute("ForumUserSession");
		ForumPost forum = forumService.findOneForumPost(id, user.getIdx());
//		for(ForumPost cf : forum.getChildren()) {
//			// TODO 지우기
//			//System.out.println("child title : "+cf.getTitle());
//		}
//		System.out.println("reco : "+forum.getRecommended());
//		System.out.println("reco cnt :"+forum.getRecommendedCount());
//		for(ForumRecommend reco : forum.getForumRecommend()) {
//			System.out.println("subs : "+ reco.getUser().getNickname());
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
		ForumUserDTO user = (ForumUserDTO)session.getAttribute("ForumUserSession");
		if(user == null) {
			// TODO 필요 한가?
			return "session error";
		}
		forum.setUser_idx(user.getIdx()); // 게시자
		forum.setType(0); // 타입
		forum.setState(0); // 상태
		
		return forumService.writeForum(forum);
	}
	
	@GetMapping("/main/edit")
	public String ForumPostModifyPage() {
		return "test";
	}
	@PostMapping("/main/edit")
	public String ForumPostModifyPost() {
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
		return "index";
	}
	
	/* 마이 페이지 */
	@GetMapping("/mypage")
	public String ForumMypageMain(Model model, HttpSession session) {
		ForumUserDTO user = (ForumUserDTO)session.getAttribute("ForumUserSession");
		ForumUserGrade grade = forumService.findOneGrade(user.getGrade_idx());
		model.addAttribute("grade", grade);
		return "/mypage/index";
	}
	
	/* 마이 페이지 */
	@GetMapping("/mypage/profile")
	public String ForumMypagePage(Model model, HttpSession session) {
		ForumUserDTO user = (ForumUserDTO)session.getAttribute("ForumUserSession");
		Long recommendCnt = forumService.findMyForumRecommendedCount(user.getIdx());
		model.addAttribute("recommendCount", recommendCnt);
		model.addAttribute("questionCount", forumService.findMyForumCountSetType(user.getIdx(), 0));
		model.addAttribute("answerCount", forumService.findMyForumCountSetType(user.getIdx(), 100));
		List<ForumPost> questionList = forumService.findMyFormList(0, 5, user.getIdx(), 0);
		model.addAttribute("questionList", questionList);
		List<ForumPost> answerList = forumService.findMyFormList(0, 5, user.getIdx(), 100);
		model.addAttribute("answerList", answerList);

		return "mypage/my_profile";
	}
	
	/* 나의 질문 */
	@GetMapping("/mypage/question")
	public String ForumMyQuestionPage(Model model, HttpSession session) {
		ForumUserDTO user = (ForumUserDTO)session.getAttribute("ForumUserSession");
		Long recommendCnt = forumService.findMyForumRecommendedCount(user.getIdx());
		model.addAttribute("recommendCount", recommendCnt);
		model.addAttribute("questionCount", forumService.findMyForumCountSetType(user.getIdx(), 0));
		model.addAttribute("answerCount", forumService.findMyForumCountSetType(user.getIdx(), 100));
		List<ForumPost> questionList = forumService.findMyFormList(0, 5, user.getIdx(), 0);
		model.addAttribute("questionList", questionList);
		List<ForumPost> answerList = forumService.findMyFormList(0, 5, user.getIdx(), 100);
		model.addAttribute("answerList", answerList);
		
		List<ForumPost> questions = forumService.findMyQuestionHeaderList(0, 9999, user.getIdx());
		model.addAttribute("questions", questions);
		
		return "mypage/my_question";
	}
	
	/* 나의 포럼 */
	@GetMapping("/mypage/forum")
	public String ForumMyforumPage(Model model, HttpSession session) {
		ForumUserDTO user = (ForumUserDTO)session.getAttribute("ForumUserSession");
		Long recommendCnt = forumService.findMyForumRecommendedCount(user.getIdx());
		model.addAttribute("recommendCount", recommendCnt);
		model.addAttribute("questionCount", forumService.findMyForumCountSetType(user.getIdx(), 0));
		model.addAttribute("answerCount", forumService.findMyForumCountSetType(user.getIdx(), 100));
		List<ForumPost> questionList = forumService.findMyFormList(0, 5, user.getIdx(), 0);
		model.addAttribute("questionList", questionList);
		List<ForumPost> answerList = forumService.findMyFormList(0, 5, user.getIdx(), 100);
		model.addAttribute("answerList", answerList);
		
		List<ForumPost> posts = forumService.findInvolvedHeaderList(user.getIdx());
		model.addAttribute("involvedQuestions", posts);
		return "/mypage/my_forum";
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
