package com.ola.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long replyNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "community_no")
	private Community community;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	private String content;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "reg_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date regDate;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "CHAR(1) default 'N'")
    private char deleted = 'N'; // 기본값 'N'
    
    /* 비밀 댓글 */
    @Column(name = "IS_PRIVATE")
    private boolean isPrivate = false; // 기본값 false 설정

    // Getter와 Setter 추가
    public boolean isDeleted() {
        return this.deleted == 'Y';
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted ? 'Y' : 'N';
    }

}