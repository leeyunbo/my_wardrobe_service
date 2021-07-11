package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.config.auth.CustomOAuth2MemberService;
import com.cloth.wardrobe.config.auth.LoginUser;
import com.cloth.wardrobe.config.auth.dto.SessionMember;
import com.cloth.wardrobe.domain.community.PostType;
import com.cloth.wardrobe.dto.clothes.WardrobeGetRequestDto;
import com.cloth.wardrobe.service.CommunityService;
import com.cloth.wardrobe.service.WardrobeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {

    private final CommunityService communityService;
    private final WardrobeService wardrobeService;
    private final CustomOAuth2MemberService customOAuth2MemberService;

    @GetMapping("/")
    public String home(Model model, @LoginUser SessionMember sessionMember) {
        if(sessionMember != null) {
            model.addAttribute("user", sessionMember);
        }

        return "home";
    }

    @GetMapping("/wardrobes/save")
    public String wardrobesSave() {
        return "wardrobe/wardrobes-save";
    }

    @GetMapping("/wardrobes")
    public String wardrobes(Model model, @RequestParam(name = "page_number") int page) {
        Pageable pageable = PageRequest.of(page-1,10);
        model.addAttribute("wardrobes", wardrobeService.findAll(pageable));
        return "wardrobe/wardrobes-list";
    }

    @GetMapping("/wardrobes/{id}")
    public String wardrobeById(Model model, @PathVariable(name = "id") Long postId, @LoginUser SessionMember sessionMember) {
        Long memberId = customOAuth2MemberService.getMemberBySession(sessionMember).getId();
        boolean isLikeUser = communityService.isLikeUsers(postId, memberId, PostType.Wardrobe);

        WardrobeGetRequestDto wardrobeGetRequestDto = wardrobeService.findById(postId);
        wardrobeGetRequestDto.setLikeUser(isLikeUser);

        model.addAttribute("wardrobe", wardrobeGetRequestDto);
        return "wardrobe/wardrobe-detail-view";
    }

    @GetMapping("/member/wardrobe")
    public String wardrobeByMember(Model model, @LoginUser SessionMember sessionMember) {
        try {
            if (sessionMember != null) {
                WardrobeGetRequestDto wardrobeGetRequestDto = wardrobeService.findByMember(sessionMember);
                model.addAttribute("wardrobe", wardrobeGetRequestDto);
                return "wardrobe/wardrobe-detail-view";
            } else {
                return "home";
            }
        }
        catch (IllegalArgumentException e) {
            return "wardrobe/wardrobes-save";
        }
    }

    @GetMapping("/wardrobe/{id}/clothes")
    public String clothesByWardrobe(Model model, @PathVariable(name = "id") Long wardrobeId) {
        WardrobeGetRequestDto wardrobeGetRequestDto = wardrobeService.findById(wardrobeId);
        model.addAttribute("wardrobe", wardrobeGetRequestDto);
        return "cloth/cloth-list";
    }

    @GetMapping("/wardrobe/{id}/clothes/add")
    public String addClothOfWardrobe(Model model, @PathVariable(name = "id") Long wardrobeId) {
        WardrobeGetRequestDto wardrobeGetRequestDto = wardrobeService.findById(wardrobeId);
        model.addAttribute("wardrobe", wardrobeGetRequestDto);
        return "cloth/cloth-save";
    }
}
