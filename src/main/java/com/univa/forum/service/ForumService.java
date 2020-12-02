package com.univa.forum.service;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;

import com.univa.forum.domain.ForumUser;
import com.univa.forum.dto.ForumUserDTO;
import com.univa.forum.repository.ForumRepository;

@Transactional
public class ForumService {
	private final ForumRepository forumRepository;
	
	public ForumService(ForumRepository forumRepository) {
		this.forumRepository = forumRepository;
	}
	
	public ForumUser userSignup(ForumUserDTO userDto) {
		ForumUser mUser = new ForumUser();
		mUser.setUsername(userDto.getUsername());
		mUser.setPassword(userDto.getPassword());
		mUser.setNickname(userDto.getNickname());
		mUser.setEmail(userDto.getEmail());
		//mUser.setImage_url(userDto.getImage_url());
		mUser.setNation(userDto.getNation());
		
		if(validateDuplicateUser(mUser)) {
			return forumRepository.save(mUser);
		} else {
			return null;
		}
	}
	
	public Optional<ForumUserDTO> userSignin(ForumUserDTO userDto) {
		ForumUser mUser = new ForumUser();
		mUser.setUsername(userDto.getUsername());
		mUser.setPassword(userDto.getPassword());
		
		Optional<ForumUser> foundUser = forumRepository.findByUsernameAndPassword(mUser);
		if(foundUser.isPresent()) {
			ForumUser user = foundUser.get();
			ForumUserDTO returnUserDto = new ForumUserDTO();
			returnUserDto.setUsername(user.getUsername());
			returnUserDto.setNickname(user.getNickname());
			returnUserDto.setEmail(user.getEmail());
			returnUserDto.setImage_url(user.getImage_url());
			returnUserDto.setGrade_idx(user.getIdx());
			returnUserDto.setNation(user.getNation());
			returnUserDto.setState(user.getState());
			returnUserDto.setDate(user.getDate());
			return Optional.ofNullable(returnUserDto);
		} else {
			return Optional.empty();
		}
	}
	
	/* 중복검사 */
	public Boolean validateDuplicateUser(ForumUser user) {
		return !forumRepository.findByUsername(user.getUsername()).isPresent();
	}
	
}
