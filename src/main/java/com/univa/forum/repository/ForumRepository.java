package com.univa.forum.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.univa.forum.domain.ForumPost;
import com.univa.forum.domain.ForumSubject;
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
	
	public ForumPost save(ForumPost forum) {
		em.persist(forum);;
		return forum;
	}
	
	public Optional<ForumUser> findUserByIdx(int idx) {
		ForumUser user = em.find(ForumUser.class, idx);
		return Optional.ofNullable(user);
	}
	
	public Optional<ForumUser> findUserByUsername(String username) {
		List<ForumUser> result = em.createQuery("select u from user u where u.username = :username", ForumUser.class)
				.setParameter("username", username)
				.getResultList();
		return result.stream().findAny();
	}
	
	public Optional<ForumUser> findUserByUsernameAndPassword(ForumUser user) {
		return em.createQuery("select u from user u where username = :username AND password = :password", ForumUser.class)
		.setParameter("username", user.getUsername())
		.setParameter("password", user.getPassword()).getResultList().stream().findAny();
	}
	
	public Optional<ForumUserGrade> findGradeByIdx(int idx) {
		ForumUserGrade grade = em.find(ForumUserGrade.class, idx);
		return Optional.ofNullable(grade);
	}
	
	public Optional<ForumPost> findForumByIdx(int idx) {
		ForumPost forum = em.find(ForumPost.class, idx);
		return Optional.ofNullable(forum);
	}
	
	public List<ForumSubject> findAllSubject() {
		return em.createQuery("select s from subject s", ForumSubject.class).getResultList();
	}
	
	public Optional<ForumSubject> findSubjectByIdx(int idx) {
		ForumSubject subject = em.find(ForumSubject.class, idx);
		return Optional.ofNullable(subject);
	}
	
	public List<ForumPost> findAllForumPost() {
		return em.createQuery("select f from forum f order by f.idx desc", ForumPost.class).getResultList();
	}
	
	public List<ForumPost> findForumPostByTypeSetLimit(int type, int firstIdx, int max) {
		return em.createQuery("select f from forum f where type = :type order by f.idx desc", ForumPost.class)
				.setParameter("type", type)
				.setFirstResult(firstIdx)
				.setMaxResults(max)
				.getResultList();
	}
	public List<ForumPost> findForumHeaderListSetLimit(int firstIdx, int max) {
		return em.createQuery("select f from forum f where parent_idx is null order by f.idx desc", ForumPost.class)
				.setFirstResult(firstIdx)
				.setMaxResults(max)
				.getResultList();
	}
	
	public List<ForumPost> findForumBySubject(int firstIdx, int max, int[] subjects) {
		String query = "select distinct f from forum f, forum_subject fs ";
		if(subjects.length > 0) {
			query += "where ( ";
			for(int i=0; subjects.length>i; i++) {
				query += "fs.subject ="+subjects[i];
				if(i < subjects.length-1) query += " OR ";
			}
			query += ") and fs.forum = f.idx ";
		}
		query += "order by f.idx desc";
		return em.createQuery(query, ForumPost.class)
				.setFirstResult(firstIdx)
				.setMaxResults(max)
				.getResultList();
	}
}
