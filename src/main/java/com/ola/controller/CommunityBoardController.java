package com.ola.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ola.entity.Community;
import com.ola.entity.Reply;
import com.ola.entity.TradeBoard;
import com.ola.repository.CommunityRepository;
import com.ola.repository.ReplyRepository;
import com.ola.repository.TradeBoardRepository;
import com.ola.security.SecurityUser;
import com.ola.service.board.CommunityBoardService;
import com.ola.service.community.CommunityService;

@Controller
public class CommunityBoardController {

	@Autowired
	private CommunityBoardService boardService;
	@Autowired
	private TradeBoardRepository boardRepo;
	@Autowired
	private CommunityRepository comRepo;
	@Autowired
	private CommunityService communityService;
	@Autowired
	private ReplyRepository replyRepository;
	

	@GetMapping("/tradeBoardList")
	public String tradeBoardList(Model model, Authentication authentication,
			@RequestParam(name = "search", required = false) String search,
			@RequestParam(name = "searchType", defaultValue = "title") String searchType,
			@PageableDefault(size = 10, sort = "registrationDate", direction = Direction.DESC) Pageable pageable) {
		if (authentication == null || !authentication.isAuthenticated()) {
			return "redirect:/system/login";
		}

		List<Community> adminWrite = comRepo.findByAdminWrite();
		model.addAttribute("adminWrite", adminWrite);

		Page<TradeBoard> tradeBoards;
		if (search != null && !search.isEmpty()) {
			if ("author".equals(searchType)) {
				tradeBoards = boardService.getTradeBoardByAuthor(search, pageable);
			} else {
				tradeBoards = boardService.getTradeBoardByTitle(search, pageable);
			}
		} else {
			tradeBoards = boardRepo.findByMemberWrite(pageable);
		}

		model.addAttribute("tradeBoards", tradeBoards);
		model.addAttribute("memberCurrentPage", tradeBoards.getNumber() + 1);
		model.addAttribute("memberTotalPages", tradeBoards.getTotalPages());
		model.addAttribute("search", search); // 검색어를 모델에 추가

		return "board/tradeBoardList";
	}

	@GetMapping("/communityBoardList")
	public String communityBoardList(Model model, Authentication authentication,
			@RequestParam(name = "search", required = false) String search,
			@RequestParam(name = "searchType", defaultValue = "title") String searchType,
			@RequestParam Optional<String> sortOrder,
			@PageableDefault(size = 10, sort = "regDate", direction = Direction.DESC) Pageable pageable) {
		if (authentication == null || !authentication.isAuthenticated()) {
			// 사용자가 로그인하지 않았거나 인증되지 않았을 경우, 로그인 페이지로 리다이렉트
			return "redirect:/system/login";
		}

		 String currentSortOrder = sortOrder.orElse("regDate,desc");
		    model.addAttribute("currentSortOrder", currentSortOrder);

		    
		// 정렬 옵션 처리
	    if (sortOrder.isPresent()) {
	        String[] sort = sortOrder.get().split(",");
	        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), 
	                                  Sort.Direction.fromString(sort[1]), sort[0]);
	    }
	    
		List<Community> adminWrite = comRepo.findByAdminWrite();
		model.addAttribute("adminWrite", adminWrite);

		Page<Community> communities;
		if (search != null && !search.isEmpty()) {
			if ("author".equals(searchType)) {
				communities = boardService.getBoardByAuthor(search, pageable);
			} else {
				communities = boardService.getBoardByTitle(search, pageable);
			}
		} else {
			communities = comRepo.findByMemberWrite(pageable);
		}
		model.addAttribute("communities", communities);
		model.addAttribute("memberCurrentPage", communities.getNumber() + 1);
		model.addAttribute("memberTotalPages", communities.getTotalPages());
		model.addAttribute("search", search); // 검색어를 모델에 추가

		return "board/communityBoardList";
	}
	
	@GetMapping("/sortedCommunityBoardList")
	public String sortedCommunityBoardList(Model model, 
	                                       @RequestParam Optional<String> sortOrder,
	                                       @PageableDefault(size = 10) Pageable pageable) {
	    String[] sort = sortOrder.orElse("regDate,desc").split(",");
	    pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), 
	                              Sort.Direction.fromString(sort[1]), sort[0]);

	    Page<Community> communities = communityService.getSortedCommunities(pageable);
	    model.addAttribute("communities", communities);
	    return "board/communityBoardList";
	}
	

	@GetMapping("/getTradeBoard")
	public String getTradeBoardView(@RequestParam("tradeBoardNo") Long tradeBoardNo, Model model) {
	    TradeBoard tradeBoard = boardRepo.findById(tradeBoardNo).orElse(null);

	    if (tradeBoard != null) {
	        List<Reply> parentReplies = replyRepository.findByTradeBoardAndParentIsNull(tradeBoard);

	        // 각 부모 댓글에 대한 대댓글 조회
	        parentReplies.forEach(parentReply -> {
	            List<Reply> childReplies = replyRepository.findChildrenByParentReplyNo(parentReply.getReplyNo());
	            parentReply.setChildren(new HashSet<>(childReplies)); // Set으로 변환하여 설정
	        });

	        model.addAttribute("tradeBoard", tradeBoard);
	        model.addAttribute("replies", parentReplies); // 부모 댓글 및 대댓글 목록 전달
	        return "board/getTradeBoard";
	    } else {
	        return "errorPage";
	    }
	}




	@GetMapping("/getCommuBoard")
	public String getCommunity(@RequestParam("communityNo") Long communityNo, Model model, Authentication authentication) {
		Community community = boardService.getCommunityWithRepliesByNo(communityNo);

	    if (community != null) {
	    	String currentUserId = authentication.getName();
	        boolean isLikedByCurrentUser = community.getLikedByMembers().stream()
	                .anyMatch(member -> member.getMemberId().equals(currentUserId));
	        List<Reply> parentReplies = replyRepository.findByCommunityAndParentIsNull(community);

	        // 각 부모 댓글에 대한 대댓글 조회
	        parentReplies.forEach(parentReply -> {
	            List<Reply> childReplies = replyRepository.findChildrenByParentReplyNo(parentReply.getReplyNo());
	            parentReply.setChildren(new HashSet<>(childReplies)); // Set으로 변환하여 설정
	        });

	        model.addAttribute("community", community);
	        model.addAttribute("isLikedByCurrentUser", isLikedByCurrentUser);
	        model.addAttribute("currentUserId", currentUserId); // 현재 로그인한 사용자의 아이디 추가
	        model.addAttribute("replies", parentReplies); // 부모 댓글 및 대댓글 목록 전달
	        return "board/getCommuBoard";
	    } else {
	        return "errorPage";
	    }
	}

	@GetMapping("/board/communityInsert")
	public String communityInsertView() {
		return "board/communityInsert";
	}

	@GetMapping("/board/tradeInsert")
	public void tradeInsertView() {

	}

	@GetMapping("/editBoard/{communityNo}")
	public String editCommunityForm(@PathVariable Long communityNo, Model model,
			@AuthenticationPrincipal UserDetails userDetails) {
		Community community = boardService.getCommunityById(communityNo);

		if (community != null) {
			// 현재 로그인한 사용자가 커뮤니티 작성자인지 확인
			if (userDetails != null && userDetails.getUsername().equals(community.getMember().getMemberId())) {
				model.addAttribute("community", community);
				return "/board/editCommunityForm";
			} else {
				// 권한이 없는 경우 에러 페이지로 리다이렉션하거나 권한 없음을 처리할 수 있습니다.
				return "errorPage";
			}
		} else {
			return "errorPage";
		}
	}

	@GetMapping("/updateBoard/{tradeBoardNo}")
	public String updateTradeForm(@PathVariable Long tradeBoardNo, Model model, Authentication authentication) {
		// 현재 로그인한 사용자의 정보를 가져오기
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		TradeBoard tradeBoard = boardService.getTradeBoardById(tradeBoardNo);

		if (tradeBoard != null && userDetails != null
				&& userDetails.getUsername().equals(tradeBoard.getMember().getMemberId())) {
			model.addAttribute("tradeBoard", tradeBoard);
			return "/board/updateTradeForm";
		} else {
			return "redirect:/getTradeBoard?tradeBoardNo=" + tradeBoardNo;
		}
	}

	@PostMapping("/editCommunity/save")
	public String saveEditedCommunity(@RequestParam Long communityNo, @RequestParam String newContent,
			@RequestParam String newTitle, @AuthenticationPrincipal UserDetails userDetails) {
		Community community = boardService.getCommunityById(communityNo);

		// 로그인한 사용자가 게시글 작성자인지 확인
		if (userDetails != null && community != null
				&& userDetails.getUsername().equals(community.getMember().getMemberId())) {
			// 여기에서 수정 작업 수행
			community.setTitle(newTitle);
			community.setContent(newContent);
			boardService.saveCommunity(community);
			return "redirect:/getCommuBoard?communityNo=" + communityNo;
		} else {
			// 사용자가 게시글을 수정할 권한이 없는 경우 처리
			// 예를 들어 에러 페이지로 리다이렉션하거나 에러 메시지를 표시할 수 있습니다.
			return "redirect:/error";
		}
	}

	@PostMapping("/updateBoard")
	public String updateTrade(@RequestParam("title") String title, @RequestParam("newContent") String content,
			@ModelAttribute TradeBoard tradeBoard) {
		tradeBoard.setTitle(title);
		tradeBoard.setContent(content);
		boardService.updateBoard(tradeBoard);
		return "redirect:/getTradeBoard?tradeBoardNo=" + tradeBoard.getTradeBoardNo();
	}

	@PostMapping("/communityInsert")
	public String communityInsertAction(@ModelAttribute Community board,
			@AuthenticationPrincipal SecurityUser principal) {

		board.setRegDate(new Date());

		board.setMember(principal.getMember());

		boardService.insertBoard(board);

		return "redirect:communityBoardList";
	}

	@PostMapping("/board/tradeInsert")
	public String tradeInsertAction(@ModelAttribute TradeBoard board, @AuthenticationPrincipal SecurityUser principal) {

		board.setMember(principal.getMember());
		board.setRegistrationDate(new Date());
		boardService.insertTradeBoard(board);

		return "redirect:/tradeBoardList";
	}

	// 게시글 삭제
	@PostMapping("/deleteBoard")
	public String deleteBoard(@RequestParam Long communityNo, @AuthenticationPrincipal UserDetails userDetails) {
		Community community = boardService.getCommunityById(communityNo);

		// 로그인한 사용자가 게시글 작성자인지 확인
		if (userDetails != null && community != null
				&& userDetails.getUsername().equals(community.getMember().getMemberId())) {
			boardService.deleteCommunity(communityNo);
			return "redirect:/communityBoardList";
		} else {
			return "redirect:/error";
		}
	}

	@PostMapping("/deleteTradeBoard")
	public String deleteTradeBoard(@RequestParam("tradeBoardNo") Long tradeBoardNo,
			@AuthenticationPrincipal UserDetails userDetails) {
		TradeBoard tradeBoard = boardService.getTradeBoardById(tradeBoardNo);

		// 로그인한 사용자가 삭제하려는 게시글의 작성자인지 확인
		if (userDetails != null && tradeBoard != null
				&& userDetails.getUsername().equals(tradeBoard.getMember().getMemberId())) {
			boardService.deleteBoard(tradeBoardNo);
			return "redirect:/tradeBoardList";
		} else {
			return "redirect:/getTradeBoard?tradeBoardNo=" + tradeBoardNo; // 로그인되지 않은 사용자라면 로그인 페이지로 리다이렉트 또는 다른 처리
		}
	}

	@PostMapping("/toggleLike")
	@ResponseBody
	public Map<String, Boolean> toggleLike(@RequestParam Long communityNo, Authentication authentication) {
		String memberId = authentication.getName();
		boolean liked = boardService.toggleLike(communityNo, memberId);
		Map<String, Boolean> response = new HashMap<>();
		response.put("liked", liked);
		return response;
	}

}
