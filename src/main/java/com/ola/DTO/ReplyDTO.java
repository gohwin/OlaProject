package com.ola.DTO;

import java.util.Date;

public class ReplyDTO {
	private Long communityNo;
	private String memberName;
	private String content;
	private Date regDate;

	public Long getCommunityNo() {
		return communityNo;
	}

	public void setCommunityNo(Long communityNo) {
		this.communityNo = communityNo;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "ReplyDTO [communityNo=" + communityNo + ", memberName=" + memberName + ", content=" + content
				+ ", regDate=" + regDate + "]";
	}

}
