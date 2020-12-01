package com.univa.forum.service;

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
	
	/* 중복검사 */
	public Boolean validateDuplicateUser(ForumUser user) {
		return !forumRepository.findByUsername(user.getUsername()).isPresent();
	}
	
}
