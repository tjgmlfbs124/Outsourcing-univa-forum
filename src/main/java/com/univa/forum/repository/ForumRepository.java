package com.univa.forum.repository;

import javax.persistence.EntityManager;

public class ForumRepository {
	private final EntityManager em;
	
	public ForumRepository(EntityManager em) {
		this.em = em;
	}
}
