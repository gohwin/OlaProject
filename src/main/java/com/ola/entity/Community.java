package com.ola.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Community {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long communityNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "memberId")
	private Member member;

	private String title;

	private String content;

	private int viewCount;

	private int commentCount;

	private int likeCount;
	
	@Transient
	private boolean likedByCurrentUser;

	public boolean isLikedByCurrentUser() {
		return likedByCurrentUser;
	}

	public void setLikedByCurrentUser(boolean likedByCurrentUser) {
		this.likedByCurrentUser = likedByCurrentUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "reg_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date regDate;

	@OneToMany(mappedBy = "community", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Reply> replies = new ArrayList<>();

	public void updateCommentCount() {
		this.commentCount = getCommentCount(); // 댓글 수 업데이트
	}

	@ManyToMany
	@JoinTable(name = "community_likes", joinColumns = @JoinColumn(name = "community_id"), inverseJoinColumns = @JoinColumn(name = "member_id"))
	private Set<Member> likedByMembers = new HashSet<>();
}