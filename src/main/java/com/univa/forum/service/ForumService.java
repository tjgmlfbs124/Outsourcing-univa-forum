package com.univa.forum.service;

import org.springframework.transaction.annotation.Transactional;

import com.univa.forum.repository.ForumRepository;

@Transactional
public class ForumService {
	private final ForumRepository forumRepository;
	
	public ForumService(ForumRepository forumRepository) {
		this.forumRepository = forumRepository;
	}
	
	
}
