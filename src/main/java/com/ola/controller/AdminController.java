package com.ola.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ola.entity.Community;
import com.ola.entity.TradeBoard;
import com.ola.repository.CommunityRepository;
import com.ola.repository.TradeBoardRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

	@Autowired
    private CommunityRepository communityRepo;

	@Autowired
	private TradeBoardRepository tradeRepo;
	
	@GetMapping("/adminMain")
    public String adminMain() {
        return "adminMain"; // 이 부분은 실제 리턴하는 뷰의 이름입니다.
    }
	
	@GetMapping("/adminCommunityBoardList")
	public String adminCommuView(Model model, HttpServletRequest request) {
	    final int DEFAULT_PAGE_NUMBER = 0; // 페이지 번호 기본값
	    final int DEFAULT_PAGE_SIZE = 10;  // 페이지 크기 기본값

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
	    Page<Community> communities = communityRepo.findAll(pageable);

	    model.addAttribute("communities", communities.getContent());
	    model.addAttribute("currentPage", page + 1); // 사용자 친화적인 페이지 번호로 변환
	    model.addAttribute("totalPages", communities.getTotalPages());

	    return "admin/adminCommunityBoardList"; // Thymeleaf 템플릿 파일 이름
	}
    
	@GetMapping("/adminTradeBoardList")
	public String adminTradeView(Model model, HttpServletRequest request) {
	    final int DEFAULT_PAGE_NUMBER = 0; // 페이지 번호 기본값
	    final int DEFAULT_PAGE_SIZE = 10;  // 페이지 크기 기본값

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
    
    @GetMapping("/deleteBoard")
    public String deleteBoard(@RequestParam Long communityNo) {
        communityRepo.deleteById(communityNo); // 게시글 삭제

        return "redirect:adminCommunityBoardList"; // 삭제 후 목록 페이지로 리디렉션
    }

}
