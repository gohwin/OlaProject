package com.ola.DTO;

import java.util.Date;

public class CommunityDTO {
	private Long communityNo;
	private String title;
	private String content;
	private Date regDate;

	public CommunityDTO() {
	}

	// 생성자
	public CommunityDTO(Long communityNo, String title, String content, Date regDate) {
		this.communityNo = communityNo;
		this.title = title;
		this.content = content;
		this.regDate = regDate;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Long getCommunityNo() {
		return communityNo;
	}

	public void setCommunityNo(Long communityNo) {
		this.communityNo = communityNo;
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

}
