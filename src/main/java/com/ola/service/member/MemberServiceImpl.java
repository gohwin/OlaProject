package com.ola.service.member;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ola.entity.Member;
import com.ola.repository.MemberRepository;
import com.ola.repository.CommunityRepository;
import com.ola.repository.LikeRepository;
import com.ola.repository.OrderListRepository;
import com.ola.repository.ReplyRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CommunityRepository communityRepository; // 게시글(커뮤니티) 저장소

	@Autowired
	private OrderListRepository orderListRepository; // 주문 저장소

	@Autowired
	private ReplyRepository replyRepository; // 댓글 저장소

	@Autowired
	private LikeRepository likeRepository; // 댓글 저장소

	@Override
	public boolean isMemberIdExists(String memberId) {
		return memberRepository.existsByMemberId(memberId);
	}

	@Override
	public String findMemberIdByNameAndEmail(String name, String email) {
		// 이름과 이메일을 사용하여 멤버 조회
		Member member = memberRepository.findByNameAndEmail(name, email);

		if (member != null) {
			return member.getMemberId(); // 또는 사용자 정의 ID 필드
		} else {
			return null;
		}
	}

	@Override
	public boolean validateUser(String memberId, String name, String email) {
		// 데이터베이스에서 사용자 조회
		Optional<Member> member = memberRepository.findByMemberIdAndNameAndEmail(memberId, name, email);

		// 사용자가 존재하면 true, 그렇지 않으면 false 반환
		return member.isPresent();
	}

	@Override
	public boolean updatePassword(String memberId, String newPassword) {
		Member member = memberRepository.findByMemberId(memberId);
		if (member == null) {
			return false;
		}

		// 비밀번호 해시 처리
		String hashedPassword = passwordEncoder.encode(newPassword);
		member.setPassword(hashedPassword);
		memberRepository.save(member);
		return true;
	}

	@Override
	public List<Member> getAllMembers() {
		return memberRepository.findAll();
	}

	@Transactional
	@Override
	public void deleteMemberById(String memberId) {
	    try {
	        log.info("🔎 회원 삭제 전 데이터 정리 시작: {}", memberId);

	        // 🔹 1. 회원이 남긴 댓글 삭제
	        replyRepository.deleteByMember(memberId);
	        log.info("🗑️ 회원의 댓글 삭제 완료");

	        // 🔹 2. 회원이 작성한 게시글 삭제
	        communityRepository.deleteByMember(memberId);
	        log.info("🗑️ 회원의 게시글 삭제 완료");

	        // 🔹 3. 회원의 좋아요 삭제
	        likeRepository.deleteByMember(memberId);
	        log.info("🗑️ 회원의 좋아요 삭제 완료");

	        // 🔹 4. 회원의 주문 삭제
	        orderListRepository.deleteByMember(memberId);
	        log.info("🗑️ 회원의 주문 삭제 완료");

	        // 🔹 5. 회원 삭제
	        memberRepository.deleteById(memberId);
	        log.info("✅ 회원 삭제 완료: {}", memberId);

	    } catch (Exception e) {
	        log.error("❌ 회원 삭제 실패: {} - {}", memberId, e.getMessage());
	        throw new RuntimeException("회원 삭제 중 오류 발생: " + e.getMessage());
	    }
	}

	@Override
	public Optional<Member> findById(String memberId) {
		return memberRepository.findById(memberId);
	}
}
