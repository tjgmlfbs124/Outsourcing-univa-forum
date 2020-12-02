package com.univa.forum.service;

import java.io.File;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.univa.forum.domain.ForumUser;
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
	
	/* 이미지 저장, url 리턴 */
	public String imageFileWrite(MultipartFile file) {
		String dirPath = "uploads/imgs";
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
	
	public Optional<ForumUserDTO> userSignin(ForumUserDTO userDto) {
		ForumUser mUser = new ForumUser();
		mUser.setUsername(userDto.getUsername());
		mUser.setPassword(userDto.getPassword());
		
		Optional<ForumUser> foundUser = forumRepository.findByUsernameAndPassword(mUser);
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
	
	public Boolean validateUserPassword(ForumUserDTO userDto, String password) {
		int userIdx = userDto.getIdx();
		Optional<ForumUser> forumUser = forumRepository.findByIdx(userIdx); 
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
	
	/* 중복검사 */
	public Boolean validateDuplicateUser(ForumUser user) {
		return !forumRepository.findByUsername(user.getUsername()).isPresent();
	}
	
}
