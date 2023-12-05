package com.ola.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ola.entity.Community;
import com.ola.entity.Member;
import com.ola.entity.TradeBoard;
import com.ola.repository.CommunityRepository;
import com.ola.repository.TradeBoardRepository;
import com.ola.security.SecurityUser;
import com.ola.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

	@Autowired
	private CommunityRepository communityRepo;

	@Autowired
	private TradeBoardRepository tradeRepo;

	@Autowired
	private BoardService boardService;

	@GetMapping("/adminMain")
	public String adminMain() {
		return "adminMain"; // 이 부분은 실제 리턴하는 뷰의 이름입니다.
	}

	@GetMapping("/adminCommunityBoardList")
	public String adminCommuView(Model model, @PageableDefault(size = 10) Pageable pageable) {
		// admin이 작성한 게시글 가져오기
		List<Community> adminWrite = communityRepo.findByAdminWrite();
		model.addAttribute("adminWrite", adminWrite);

		// 수정된 메소드를 호출하고 TradeBoard로 변환하여 결과를 가져옴
		Page<Community> memberWrite = communityRepo.findByMemberWrite(pageable);
		model.addAttribute("memberWrite", memberWrite);
		model.addAttribute("memberCurrentPage", memberWrite.getNumber() + 1);
		model.addAttribute("memberTotalPages", memberWrite.getTotalPages());

		return "admin/adminCommunityBoardList"; // View 반환
	}

	@GetMapping("/adminTradeBoardList")
	public String adminTradeView(Model model, HttpServletRequest request) {
		final int DEFAULT_PAGE_NUMBER = 0; // 페이지 번호 기본값
		final int DEFAULT_PAGE_SIZE = 10; // 페이지 크기 기본값

		int page = DEFAULT_PAGE_NUMBER;
		int size = DEFAULT_PAGE_SIZE;

		try {
			String pageParam = request.getParameter("page");
			String sizeParam = request.getParameter("size");

			page = (pageParam != null) ? Integer.parseInt(pageParam) - 1 : DEFAULT_PAGE_NUMBER;
			size = (sizeParam != null) ? Integer.parseInt(sizeParam) : DEFAULT_PAGE_SIZE;
		} catch (NumberFormatException e) {
			// 잘못된 숫자 포맷 처리 로직 (예: 로깅, 기본값 사용 등)
		}

		Pageable pageable = PageRequest.of(page, size);
		Page<TradeBoard> tradeBoards = tradeRepo.findAll(pageable);

		model.addAttribute("tradeBoardList", tradeBoards.getContent());
		model.addAttribute("currentPage", page + 1); // 사용자 친화적인 페이지 번호로 변환
		model.addAttribute("totalPages", tradeBoards.getTotalPages());

		return "admin/adminTradeBoardList"; // Thymeleaf 템플릿 파일 이름
	}

	@GetMapping("/adminGetBoard")
	public String getBoard(@RequestParam Long communityNo, Model model) {
		Community community = communityRepo.findById(communityNo).orElse(null); // communityNo에 해당하는 Community 객체를 조회

		if (community != null) {
			model.addAttribute("community", community);
			return "admin/adminGetBoard"; // 게시글 상세보기 페이지의 뷰 이름
		} else {
			return "errorPage"; // 에러 페이지의 뷰 이름.
		}
	}

	@GetMapping("/deleteAdminBoard")
	public String deleteAdminBoard(@RequestParam Long communityNo) {
		communityRepo.deleteById(communityNo); // 게시글 삭제

		return "redirect:adminCommunityBoardList"; // 삭제 후 목록 페이지로 리디렉션
	}

	@GetMapping("/adminRegisterNotice")
	public String registerNoticeView() {
		return "admin/adminRegisterNotice";
	}

	@PostMapping("/adminRegisterNotice")
	public String registerNoticeAction(Community board, @AuthenticationPrincipal SecurityUser principal) {
		board.setMember(principal.getMember());
		board.setRegDate(new Date());
		boardService.insertBoard(board);

		return "redirect:adminCommunityBoardList";
	}
}
