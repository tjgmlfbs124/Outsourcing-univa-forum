package com.univa.forum.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.univa.forum.domain.ForumUser;
import com.univa.forum.domain.ForumUserGrade;

public class ForumRepository {
	private final EntityManager em;
	
	public ForumRepository(EntityManager em) {
		this.em = em;
	}
	
	public ForumUser save(ForumUser user) {
		em.persist(user);
		return user;
	}
	
	public Optional<ForumUser> findByIdx(int idx) {
		ForumUser user = em.find(ForumUser.class, idx);
		return Optional.ofNullable(user);
	}
	
	public Optional<ForumUser> findByUsername(String username) {
		List<ForumUser> result = em.createQuery("select u from user u where u.username = :username", ForumUser.class)
				.setParameter("username", username)
				.getResultList();
		return result.stream().findAny();
	}
	
	public Optional<ForumUser> findByUsernameAndPassword(ForumUser user) {
		return em.createQuery("select u from user u where username = :username AND password = :password", ForumUser.class)
		.setParameter("username", user.getUsername())
		.setParameter("password", user.getPassword()).getResultList().stream().findAny();
	}
	
	public Optional<ForumUserGrade> findByGradeId(int id) {
		ForumUserGrade grade = em.find(ForumUserGrade.class, id);
		return Optional.ofNullable(grade);
	}
}
