package com.ola.adminController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ola.entity.Community;
import com.ola.entity.Reply;
import com.ola.repository.CommunityRepository;
import com.ola.service.community.CommunityService;
import com.ola.service.reply.ReplyService;

@Controller
public class adminCommunityController {

	@Autowired
	private CommunityService communityService;

	@Autowired
	private ReplyService replyService;

	@Autowired
	private CommunityRepository communityRepo;

	@GetMapping("/admin/community")
	public String showCommunityList(Model model) {
		model.addAttribute("communities", communityService.getAllCommunities());
		return "/admin/community_list"; // 해당하는 Thymeleaf 템플릿 이름
	}

	/* 게시글 상세보기와 댓글 출력 */
	@GetMapping("/admin/community/detail/{communityId}")
	public String showCommunityDetail(@PathVariable("communityId") Long communityId, Model model) {
		Community community = communityService.getCommunityById(communityId);
		List<Reply> replies = replyService.getRepliesByCommunity(community);
		model.addAttribute("community", community);
		model.addAttribute("replies", replies);

		if (community != null) {
			model.addAttribute("community", community);
			return "/admin/communityDetail";
		} else {
			return "redirect:/admin/community";
		}
	}

	@PostMapping("/admin/community/delete/{communityNo}")
	public String adminDeleteCommunity(@PathVariable("communityNo") Long communityId, Model model) {
		communityRepo.deleteById(communityId);
		model.addAttribute("communities", communityService.getAllCommunities());
		return "/admin/community_list"; // 해당하는 Thymeleaf 템플릿 이름
	}

	@PostMapping("/admin/community/deleteReply")
	public String deleteReply(@RequestParam("replyNo") Long replyNo, @RequestParam("communityNo") Long communityNo) {
		replyService.deleteReply(replyNo);

		// 댓글이 속한 게시글로 리다이렉트
		return "redirect:/admin/community/detail/" + communityNo;
	}

}