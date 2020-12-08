package com.univa.forum.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.univa.forum.domain.ForumFile;
import com.univa.forum.domain.ForumPost;
import com.univa.forum.domain.ForumRecommend;
import com.univa.forum.domain.ForumSubject;
import com.univa.forum.domain.ForumSubjectBridge;
import com.univa.forum.domain.ForumUser;
import com.univa.forum.domain.ForumUserGrade;
import com.univa.forum.dto.ForumPostDTO;
import com.univa.forum.dto.ForumUserDTO;
import com.univa.forum.repository.ForumRepository;
import com.univa.forum.utils.FileUtil;
import com.univa.forum.utils.StringUtil;

@Transactional
public class ForumService {
	private final ForumRepository forumRepository;
	
	public ForumService(ForumRepository forumRepository) {
		this.forumRepository = forumRepository;
	}
	/** 회원가입 */
	public String userSignup(ForumUserDTO userDto) {
		ForumUser mUser = new ForumUser();
		mUser.setUsername(userDto.getUsername());
		mUser.setPassword(userDto.getPassword());
		mUser.setNickname(userDto.getNickname());
		mUser.setEmail(userDto.getEmail());
		if(!userDto.getFile().isEmpty()) {
			//mUser.setImage_url(userDto.getImage_url());
			mUser.setImage_url(imageFileWrite(userDto.getFile()));
		}
		mUser.setNation(userDto.getNation());
		
		if(validateDuplicateUser(mUser)) {
			if( forumRepository.save(mUser).getUsername().equals(userDto.getUsername()) ) {
				return "ok";
			} else {
				return "error";
			}
		} else {
			return "duplicaiton"; //이미 있는 유저
		}
	}
	
	/** 이미지 저장, url 리턴 */
	public String imageFileWrite(MultipartFile file) {
		String dirPath = "uploads/imgs/";
		String randomStr = StringUtil.RandomString(20)+"/";
		String imageUrl = randomStr+"img"+FileUtil.getExtension(file.getOriginalFilename()).get();
		String savePath = dirPath+imageUrl;
		try {
			File mFile = new File(dirPath+randomStr);
			mFile.mkdirs();
		} catch (Exception e) {
			e.getStackTrace();
		}
		FileUtil.FileWrite(file, savePath);
		return imageUrl;
	}
	/** 파일 저장, url 리턴 */
	public String writeFile(MultipartFile file) {
		String dirPath = "uploads/forum_files/";
		String randomStr = StringUtil.RandomString(20)+"/";
		String fileUrl = randomStr+"file"+FileUtil.getExtension(file.getOriginalFilename()).get();
		String savePath = dirPath+fileUrl;
		try {
			File mFile = new File(dirPath+randomStr);
			mFile.mkdirs();
		} catch (Exception e) {
			e.getStackTrace();
		}
		FileUtil.FileWrite(file, savePath);
		return fileUrl;
	}
	
	/** 로그인 */
	public Optional<ForumUserDTO> userSignin(ForumUserDTO userDto) {
		ForumUser mUser = new ForumUser();
		mUser.setUsername(userDto.getUsername());
		mUser.setPassword(userDto.getPassword());
		
		Optional<ForumUser> foundUser = forumRepository.findUserByUsernameAndPassword(mUser);
		if(foundUser.isPresent()) {
			ForumUser user = foundUser.get();
			ForumUserDTO returnUserDto = new ForumUserDTO();
			returnUserDto.setIdx(user.getIdx());
			returnUserDto.setUsername(user.getUsername());
			//returnUserDto.setPassword(user.getPassword());
			returnUserDto.setNickname(user.getNickname());
			returnUserDto.setEmail(user.getEmail());
			returnUserDto.setImage_url(user.getImage_url());
			returnUserDto.setGrade_idx(user.getIdx());
			returnUserDto.setNation(user.getNation());
			returnUserDto.setState(user.getState());
			returnUserDto.setDate(user.getDate());
			return Optional.ofNullable(returnUserDto);
		} else {
			return Optional.ofNullable(null);
		}
	}
	
	/** 인덱스로 게시물 찾기 */
	public ForumPost findOneForumPost(int idx, int userIdx) {
		ForumPost post = forumRepository.findForumByIdx(idx).get();
		
		post.setRecommendedCount(post.getForumRecommend().size());
		Set<ForumRecommend> recommend = post.getForumRecommend();
		for (ForumRecommend reco : recommend ) {
			if (reco.getUser().getIdx() == userIdx) { 
				post.setRecommended(true);
			}
		}
		
		return post;
	}
	
	/** 가장 자식이 많은 게시물 역순 정렬 */
	public List<ForumPost> findHeaderForumOrderByChildCnt(int user_idx) {
		List<ForumPost> posts = this.findHeaderForumList(user_idx);
		posts = this.addChildCount(posts);
		
		posts.sort(new Comparator<ForumPost>() {
			@Override
			public int compare(ForumPost post1, ForumPost post2) {
				int cnt1 = post1.getChildrenCount();
				int cnt2 = post2.getChildrenCount();
				
				if(cnt1==cnt2) return 0;
				else if(cnt1 > cnt2) return -1;
				else return 1;
			}
		});
		return posts;
	}
	public List<ForumPost> findHeaderForumOrderByChildCnt() {
		List<ForumPost> posts = this.findHeaderForumList();
		posts = this.addChildCount(posts);
		
		posts.sort(new Comparator<ForumPost>() {
			@Override
			public int compare(ForumPost post1, ForumPost post2) {
				int cnt1 = post1.getChildrenCount();
				int cnt2 = post2.getChildrenCount();
				
				if(cnt1==cnt2) return 0;
				else if(cnt1 > cnt2) return -1;
				else return 1;
			}
		});
		return posts;
	}
	
	/** 유저 비밀번호 검사 */
	public Boolean validateUserPassword(ForumUserDTO userDto, String password) {
		int userIdx = userDto.getIdx();
		Optional<ForumUser> forumUser = forumRepository.findUserByIdx(userIdx); 
		if(forumUser.isPresent()) {
			if(forumUser.get().getPassword().equals(password)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/** 중복검사 */
	public Boolean validateDuplicateUser(ForumUser user) {
		return !forumRepository.findUserByUsername(user.getUsername()).isPresent();
	}
	
	/** 모든 주제 찾기 */
	public List<ForumSubject> findAllSubject() {
		return forumRepository.findAllSubject();
	}
	
	/** 포럼 쓰기, 결과 리턴 */
	public String writeForum(ForumPostDTO forum) {
		ForumPost forumPost = new ForumPost();
		ForumUser user = findUserByIdx(forum.getUser_idx());
		if(user == null) return "user error";
		forumPost.setUser(user);
		forumPost.setType(forum.getType());
		forumPost.setTitle(forum.getTitle());
		forumPost.setContent(forum.getContent());
		forumPost.setState(forum.getState());
		System.out.println("what??");
		for(int subs : forum.getSubjects() ) {
			ForumSubject subject = forumRepository.findSubjectByIdx(subs).get();
			if(subject != null) {
				ForumSubjectBridge subjectEnt = new ForumSubjectBridge();
				subjectEnt.setSubject(subject);
				subjectEnt.setForum(forumPost);
				forumPost.addSubjects(subjectEnt);
			}
		}
		if( forum.getFiles() != null && forum.getFiles().size() > 0) {
			for (MultipartFile file : forum.getFiles() ) {
				ForumFile mFile = new ForumFile();
				mFile.setFile_url(writeFile(file));
				mFile.setForum(forumPost);
				forumPost.addFiles(mFile);
			}
		}
		forumRepository.save(forumPost);
		
		return "ok";
	}
	
	/** 유저 찾기 */
	public ForumUser findUserByIdx(int idx) {
		return forumRepository.findUserByIdx(idx).orElse(null);
	}
	
	/** 모든 주제 포럼 게시물 리스트 */
	public List<ForumPost> findHeaderForumList(int first, int max, int user_idx) {
		List<ForumPost> posts = forumRepository.findForumHeaderListSetLimit(first, max);
		posts = this.addRecommendCount(posts, user_idx);
		
		return posts;
	}
	public List<ForumPost> findHeaderForumList(int user_idx) {
		List<ForumPost> posts = forumRepository.findForumHeaderList();
		posts = this.addRecommendCount(posts, user_idx);
		
		return posts;
	}
	public List<ForumPost> findHeaderForumList() {
		List<ForumPost> posts = forumRepository.findForumHeaderList();
		posts = this.addRecommendCount(posts);
		
		return posts;
	}
	
	/** 특정 주제 포럼 게시물 리스트 */
	public List<ForumPost> findQuestionsBySubject(int first, int max, int[] subjects, int user_idx){
		List<ForumPost> posts = forumRepository.findForumBySubject(first, max, subjects);
		posts = this.addRecommendCount(posts, user_idx);
		return posts;
	}
	
	/** 유저 등급 리턴 */
	public ForumUserGrade findOneGrade(int idx) {
		return forumRepository.findGradeByIdx(idx).orElse(null);
	}
	
	/** 나의 포럼 게시물 수 타입별 리턴 */
	public Long findMyForumCountSetType(int user_idx, int type) {
		return forumRepository.findForumCountByUserIdxSetType(user_idx, type);	
	}
	
	/** 나의 포럼 게시물 */
	public List<ForumPost> findMyFormList(int first, int max, int user_idx, int type) {
		List<ForumPost> posts = forumRepository.findForumPostByTypeSetLimit(type, first, max, user_idx);
		posts = this.addRecommendCount(posts, user_idx);
		return posts;
	}
	
	/** 나의 모든 포럼의 받은 추천 수*/
	public Long findMyForumRecommendedCount(int user_idx) {
		return forumRepository.findAllMyForumRecommendedCount(user_idx);
	}
	
	/** 나의 모든 포럼 질문 */
	public List<ForumPost> findMyQuestionHeaderList(int first, int max, int user_idx) {
		//return forumRepository.findForumHeaderListSetLimitAndUser(first, max, user_idx);
		List<ForumPost> posts = new ArrayList<ForumPost>();
		posts = forumRepository.findForumHeaderListSetLimitAndUser(first, max, user_idx);
		posts = this.addRecommendCount(posts, user_idx);
		posts = this.addChildCount(posts);
		
		return posts;
	}
	
	/** 내가 관여한 모든 포럼의 루트 질문 */
	public List<ForumPost> findInvolvedHeaderList(int user_idx) {
		List<ForumPost> allPosts = forumRepository.findForumByUserIdx(user_idx);
		List<ForumPost> involvedPosts = new ArrayList<ForumPost>();
		for(ForumPost post : allPosts) {
			ForumPost rootPost = this.getRoot(post);
			if(!involvedPosts.contains(rootPost)) involvedPosts.add(rootPost);
		}
		
		involvedPosts = this.addRecommendCount(involvedPosts, user_idx);
		involvedPosts = this.addChildCount(involvedPosts);
		
		return involvedPosts;
	}
	
	/** 게시글 과 자식 모두 추천수 추가 */
	public List<ForumPost> addRecommendCount(List<ForumPost> posts) {
		for(ForumPost post: posts) {
			post.setRecommendedCount(post.getForumRecommend().size());
			if(post.getChildren() != null) this.addRecommendCount(post.getChildren());
		}
		return posts;
	}
	
	/** 게시글 과 자식 모두 추천수, 내가 추천했는지 추가 */
	public List<ForumPost> addRecommendCount(List<ForumPost> posts, int user_idx) {
		for(ForumPost post: posts) {
			post.setRecommendedCount(post.getForumRecommend().size());
			if(user_idx > 0) {
				for(ForumRecommend reco : post.getForumRecommend()) {
					if(reco.getUser().getIdx() == user_idx) {
						post.setRecommended(true);
					}
				}
			}
			if(post.getChildren() != null) this.addRecommendCount(post.getChildren(), user_idx);
		}
		return posts;
	}
	
	/** 게시물에 모든 자식 수 할당*/
	public List<ForumPost> addChildCount(List<ForumPost> posts) {
		for(ForumPost post: posts) {
			post.setChildrenCount(this.findChildrenCount(post));
		}
		return posts;
	}
	
	/** 게시글의 모든 자식 수 */
	public int findChildrenCount(ForumPost post) {
		int count = 0;
		count += post.getChildren().size();
		for(ForumPost forumPost : post.getChildren()) {
			count += this.findChildrenCount(forumPost);
		}
		return count;
	}
	
	/** 게시글 최상위 루트 찾기 */
	public ForumPost getRoot(ForumPost post) {
		while (post.getParent() != null) {
			post = post.getParent();
		}
		return post;
	}
	
}
