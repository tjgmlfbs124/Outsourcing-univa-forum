package com.univa.forum.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="subject")
public class ForumSubject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "UNSIGNED INT")
	private int idx;
	
	@Column(name = "name", length=255)
	private String name;
}
