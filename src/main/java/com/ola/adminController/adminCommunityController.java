package com.ola.adminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ola.service.CommunityService;

@Controller
public class adminCommunityController {

	@Autowired
	private CommunityService communityService;

	@GetMapping("/admin/community")
	public String showCommunityList(Model model) {
		model.addAttribute("communities", communityService.getAllCommunities());
		return "/admin/community_list"; // 해당하는 Thymeleaf 템플릿 이름
	}
}