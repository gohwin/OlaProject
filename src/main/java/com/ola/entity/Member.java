package com.ola.entity;



import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Entity
public class Member {

    @Id
    private String memberId;

    private String password;

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private String detailedAddress;

    @Enumerated(EnumType.STRING)
	private Role role;
    
    
}