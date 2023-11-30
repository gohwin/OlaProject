package com.ola.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ola.entity.Community;
import com.ola.repository.CommunityRepository;

@Controller
public class AdminController {

	@Autowired
    private CommunityRepository communityRepo;

    @GetMapping("/adminCommunityBoardList")
    public String adminCommuView(Model model) {
        List<Community> communities = communityRepo.findAll();
        model.addAttribute("communities", communities);
        return "admin/adminCommunityBoardList"; // Thymeleaf 템플릿 파일 이름
    }
    
    @GetMapping("/adminGetBoard")
    public String getBoard(@RequestParam Long communityNo, Model model) {
        Community community = communityRepo.findById(communityNo).orElse(null); // communityNo에 해당하는 Community 객체를 조회

        if (community != null) {
            model.addAttribute("community", community);
            return "admin/adminGetBoard"; // 게시글 상세보기 페이지의 뷰 이름
        } else {
            return "errorPage"; // 에러 페이지의 뷰 이름
        }
    }
    
    @GetMapping("/deleteBoard")
    public String deleteBoard(@RequestParam Long communityNo) {
        communityRepo.deleteById(communityNo); // 게시글 삭제

        return "redirect:adminCommunityBoardList"; // 삭제 후 목록 페이지로 리디렉션
    }

}
