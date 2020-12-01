package com.univa.forum.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="forum")
public class ForumPost {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "UNSIGNED INT")
	private int idx;
	
	@ManyToOne(targetEntity=ForumUser.class, fetch=FetchType.EAGER)
	@JoinColumn(name="user_idx")
	private ForumUser user;
	
	//private ForumPost parent;
	
	@Column(columnDefinition = "UNSIGNED INT")
	private int type;
	
	@Column(name = "title", length=255)
	private String title;
	
	@Column(name = "content", columnDefinition = "TEXT")
	private String content;
	
	@Column
	private int delete_request;
	
	//private ForumPost history;
	
	@Column(name = "update_date")
	private LocalDateTime update_date;
	
	@Column(name = "state")
	private int state;
	
	@Column(name = "hits")
	private int hits;
	
}
