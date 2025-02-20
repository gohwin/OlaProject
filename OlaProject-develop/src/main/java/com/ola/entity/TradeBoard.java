package com.ola.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class TradeBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tradeBoardNo;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    private int tradeType;
    
    private String content;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registration_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date registrationDate;

    private String title;
    
    @OneToMany(mappedBy = "tradeBoard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Reply> replies = new ArrayList<>();


}