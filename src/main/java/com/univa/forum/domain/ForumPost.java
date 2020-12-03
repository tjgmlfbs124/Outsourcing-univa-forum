package com.univa.forum.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name="forum")
public class ForumPost {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "UNSIGNED INT")
	private int idx;
	
	@ManyToOne(targetEntity=ForumUser.class, fetch=FetchType.EAGER)
	@JoinColumn(name="user_idx")
	private ForumUser user;
	
	@ManyToOne(targetEntity=ForumPost.class, fetch=FetchType.LAZY)
	@JoinColumn(name="parent_idx")
	private ForumPost parent;
	
	@OneToMany(targetEntity=ForumPost.class, mappedBy = "parent", fetch=FetchType.EAGER)
	private List<ForumPost> children;
	
	@Column(columnDefinition = "UNSIGNED INT")
	private int type;
	
	@Column(name = "title", length=255)
	private String title;
	
	@Column(name = "content", columnDefinition = "TEXT")
	private String content;
	//private ForumPost history;
	
	@Column(name = "update_date")
	private LocalDateTime update_date;
	
	@Column(name = "state")
	private int state;
	
	@Column(name = "hits")
	private int hits;
	
	@OneToMany(targetEntity=ForumFile.class, mappedBy="forum", cascade = CascadeType.PERSIST)
	private List<ForumFile> files;
	
	@OneToMany(targetEntity=ForumSubjectBridge.class, mappedBy="forum", cascade = CascadeType.PERSIST)
	private Set<ForumSubjectBridge> subjects;

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public ForumUser getUser() {
		return user;
	}

	public void setUser(ForumUser user) {
		this.user = user;
	}

	public ForumPost getParent() {
		return parent;
	}

	public void setParent(ForumPost parent) {
		this.parent = parent;
	}

	public List<ForumPost> getChildren() {
		return children;
	}

	public void setChildren(List<ForumPost> children) {
		this.children = children;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(LocalDateTime update_date) {
		this.update_date = update_date;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public List<ForumFile> getFiles() {
		return files;
	}

	public void setFiles(List<ForumFile> files) {
		this.files = files;
	}

	public Set<ForumSubjectBridge> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<ForumSubjectBridge> subjects) {
		this.subjects = subjects;
	}
	
}
