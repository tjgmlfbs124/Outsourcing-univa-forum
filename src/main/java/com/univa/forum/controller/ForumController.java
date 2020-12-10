package com.univa.forum.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.univa.forum.domain.ForumPost;
import com.univa.forum.domain.ForumRecommend;
import com.univa.forum.domain.ForumSubjectBridge;
import com.univa.forum.domain.ForumUser;
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
		model.addAttribute("subject", forumService.findAllSubject());
		
		return "main/index";
	}
	
	/* 포럼 과목별 게시판 */
	@GetMapping("/main/board")
	public String ForumBoard(
			@RequestParam(value="min", defaultValue="0") int min,
			@RequestParam(value="max", defaultValue="10") int max,
			@RequestParam(value="subject", defaultValue="0") int[] subject,
			@RequestParam(value="title", defaultValue="") String title,
			@RequestParam(value="sort", defaultValue="date") String sort,
			Model model,
			HttpSession session) {
		
		int user_idx = 0;
		ForumUserDTO user = (ForumUserDTO)session.getAttribute("ForumUserSession");
		if(user != null) user_idx = user.getIdx();
		
		List<ForumPost> forums;
		if(subject[0] == 0) {
			forums = forumService.findHeaderForumList(min, max, user_idx, title, sort);
		} else {
			//forums = forumService.findQuestionsBySubject(min, max, subject, user.getIdx());
			forums = forumService.findHeaderForumList(min, max, user_idx, subject, title, sort);
		}
		
		model.addAttribute("question", forums);
		model.addAttribute("subject", forumService.findAllSubject());
		
		return "/main/board";
	}
	
	/* 포럼 내용 보기 */
	@GetMapping("/main/content") //TODO 컨텐트 보이기, 조회수 올리기
	public String ForumContent(@RequestParam("id") int id, Model model, HttpSession session) {
		ForumUserDTO user = (ForumUserDTO)session.getAttribute("ForumUserSession");
		ForumPost forum;
		
		if(user != null) {
			forum = forumService.findOneForumPost(id, user.getIdx());
		} else {
			forum = forumService.findOneForumPost(id);
		}
		
		forum.setHits(forum.getHits()+1);
		forumService.writeForum(forum);
		model.addAttribute("forum", forum);
		
		return "/main/content";
	}
	
	@GetMapping("/main/history") // TODO 히스토리
	public String ForumhistoryPage(@RequestParam("id") int id, Model model, HttpSession session) {
		//ForumUserDTO user = (ForumUserDTO)session.getAttribute("ForumUserSession");
		//ForumPost forum = forumService.findOneForumPost(id, user.getIdx());
		//model.addAttribute("forum", forum);
		ForumPost forum = forumService.findOneForumPost(id);
		List<ForumPost> history = forumService.getHisotry(forum);
		
		model.addAttribute("history", history);
		
		return "/main/history";
	}
	
	@GetMapping("/main/profile") // TODO 프로필
	public String ForumProfilePage(@RequestParam("id") int id, Model model, HttpSession session) {
		ForumUserDTO user = (ForumUserDTO)session.getAttribute("ForumUserSession");
		
		Long recommendCnt = forumService.findMyForumRecommendedCount(id);
		model.addAttribute("recommendCount", recommendCnt);
		model.addAttribute("questionCount", forumService.findMyForumCountSetType(id, 0));
		model.addAttribute("answerCount", forumService.findMyForumCountSetType(id, 100));
		List<ForumPost> questionList = forumService.findMyFormList(0, 5, id, 0);
		model.addAttribute("questionList", questionList);
		List<ForumPost> answerList = forumService.findMyFormList(0, 5, id, 100);
		model.addAttribute("answerList", answerList);
		
		return "/main/profile";
	}
	
	/* 글쓰기 페이지 */
	@GetMapping("/main/write")
	public String ForumWritePage(Model model) {
		model.addAttribute("subject", forumService.findAllSubject());
		return "/main/write";
	}
	@PostMapping(value = {"/main/write", "/main/reply"} )
	@ResponseBody
	public String ForumWritePost(
			ForumPostDTO forum,
			HttpSession session) {
		ForumUserDTO user = (ForumUserDTO)session.getAttribute("ForumUserSession");
		
		forum.setUser_idx(user.getIdx()); // 게시자
		forum.setType(0); // 타입
		forum.setState(0); // 상태
		
		return forumService.writeForum(forum);
	}
	
	/* 포럼 수정 요청 */
	@GetMapping("/main/edit")
	public String ForumPostModifyPage(@RequestParam("id") int idx, Model model, HttpSession session) {
		ForumUserDTO user = (ForumUserDTO) session.getAttribute("ForumUserSession");
		model.addAttribute(forumService.findOneForumPost(idx, user.getIdx()));
		
		return "test";
	}
	@PostMapping("/main/edit")
	@ResponseBody
	public String ForumPostModifyPost(ForumPostDTO forum, HttpSession session) {
		ForumUserDTO user = (ForumUserDTO)session.getAttribute("ForumUserSession");
		// TODO 포럼 수정 요청 테스트중
//		ForumPostDTO testForum = new ForumPostDTO();
//		testForum.setModify_parent_idx(2); // 수정 요청할 원본 게시물 번호
//		testForum.setTitle("테스트 답변1+수정");
//		testForum.setContent("1-2 +수정내용");
//		testForum.setSubjects(Arrays.asList(1)); // 주제
//		
		forum.setUser_idx(user.getIdx());
		forumService.modifyForum(forum);
		
		return "test";
	}
	
	@PostMapping("/main/modifyApply")
	@ResponseBody
	public String ForumPostModifySelectPost(@RequestParam("id") int modIdx, HttpSession session) {
		ForumUserDTO user = (ForumUserDTO)session.getAttribute("ForumUserSession");
		
		return forumService.modifyForumApproval(modIdx, user.getIdx());
	}
	
	/* 포럼 삭제요청 */
	@PostMapping("/main/remove")
	@ResponseBody
	public String ForumPostRemoveRequestPost(@RequestParam("id")int idx, HttpSession session) {
		// TODO 삭제 요청
		ForumUserDTO user = (ForumUserDTO)session.getAttribute("ForumSessionUser");
		ForumPost post = forumService.findOneForumPost(idx);
		if( post.getUser().getIdx() == user.getIdx() ) {
			post.setState(20);
		}
		
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
		return "redirect:/forum";
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
	//TODO 파일 불러오기
	
	@GetMapping("/service/{path}")
	public String serviceStaticPage(
			@PathVariable(value="path") String path) {
		return "/service/"+path;
	}
	
}
