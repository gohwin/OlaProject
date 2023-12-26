package com.ola.entity;

import java.util.Date;
import java.util.Objects;
import java.util.Set; // Set 임포트

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany; // OneToMany 임포트
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
    @JoinColumn(name = "tradeBoard_no")
    private TradeBoard tradeBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reg_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date regDate;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "CHAR(1) default 'N'")
    private char deleted = 'N'; // 기본값 'N'

    // 새로운 필드: 부모 댓글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Reply parent;

    // 자식 댓글 목록
    @OneToMany(mappedBy = "parent")
    private Set<Reply> children;

    // 삭제 여부 Getter와 Setter
    public boolean isDeleted() {
        return this.deleted == 'Y';
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted ? 'Y' : 'N';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reply reply = (Reply) o;
        return Objects.equals(replyNo, reply.replyNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(replyNo);
    }


}
