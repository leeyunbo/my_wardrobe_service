package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.config.auth.CustomOAuth2MemberService;
import com.cloth.wardrobe.config.auth.LoginUser;
import com.cloth.wardrobe.config.auth.dto.SessionMember;
import com.cloth.wardrobe.domain.community.PostType;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.dto.clothes.ResponseForCloth;
import com.cloth.wardrobe.dto.clothes.ResponseForWardrobe;
import com.cloth.wardrobe.dto.clothes.ResponseForWardrobes;
import com.cloth.wardrobe.dto.statistics.ResponseForStatistics;
import com.cloth.wardrobe.exception.BadRequestException;
import com.cloth.wardrobe.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

import static com.cloth.wardrobe.domain.community.PostType.Wardrobe;

/**
 * 웹 Front를 위한 컨트롤러
 * 웹 어플리케이션의 이동 및 서버로의 요청을 담당한다.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class WebController {

    private final RecordService recordService;
    private final CommunityService communityService;
    private final WardrobeService wardrobeService;
    private final CustomOAuth2MemberService customOAuth2MemberService;
    private final ClothService clothService;
    private final StatisticsService statisticsService;

    //== Home 관련 ==//
    @GetMapping("/")
    public String home(Model model, @LoginUser SessionMember sessionMember) {
        if(sessionMember != null) {
            model.addAttribute("user", sessionMember);
        }

        return "home";
    }

    //== Wardrobe 관련 ==//
    @GetMapping("/wardrobes/save")
    public String saveWardrobe() {
        return "wardrobe/wardrobes-save";
    }

    @GetMapping("/wardrobes")
    public String findWardrobes(Model model) {
        ResponseForWardrobes responseForWardrobes = (ResponseForWardrobes) wardrobeService.findAll().getBody();
        model.addAttribute("wardrobes", responseForWardrobes.getContents());
        return "wardrobe/wardrobes-list";
    }

    @GetMapping("/wardrobes/{id}")
    public String findWardrobeById(Model model, @PathVariable(name = "id") Long postId, @LoginUser SessionMember sessionMember) {
        Long memberId = customOAuth2MemberService.getMemberBySession(sessionMember).getId();
        boolean isLikeUser = communityService.isLikeUsers(postId, memberId, Wardrobe);

        ResponseForWardrobe responseForWardrobe = (ResponseForWardrobe) wardrobeService.findById(postId).getBody();
        responseForWardrobe.setLikeUser(isLikeUser);

        model.addAttribute("wardrobe", responseForWardrobe);
        return "wardrobe/wardrobe-detail-view";
    }

    @GetMapping("/member/wardrobe")
    public String findWardrobeByMember(Model model, @LoginUser SessionMember sessionMember) {
        try {
            ResponseForWardrobe responseForWardrobe = wardrobeService.findByMember(customOAuth2MemberService.getMemberBySession(sessionMember));
            model.addAttribute("wardrobe", responseForWardrobe);
            return "wardrobe/wardrobe-detail-view";
        }
        catch (BadRequestException e) {
            return "wardrobe/wardrobes-save";
        }
    }

    //== Cloth 관련 ==//
    @GetMapping("/clothes/{id}")
    public String findClothById(Model model, @PathVariable(name = "id") Long id, @LoginUser SessionMember sessionMember) {
        Long memberId = customOAuth2MemberService.getMemberBySession(sessionMember).getId();
        boolean isLikeUser = communityService.isLikeUsers(id, memberId, PostType.Cloth);

        ResponseForCloth responseForCloth = (ResponseForCloth) clothService.findById(id).getBody();
        responseForCloth.setLikeUser(isLikeUser);

        model.addAttribute("cloth", responseForCloth);
        return "cloth/cloth-detail-view";
    }

    @GetMapping("/wardrobe/{id}/clothes")
    public String clothesOfWardrobe(Model model, @PathVariable(name = "id") Long wardrobeId) {
        ResponseForWardrobe responseForWardrobe = (ResponseForWardrobe) wardrobeService.findById(wardrobeId).getBody();

        model.addAttribute("wardrobe", responseForWardrobe);
        return "cloth/cloth-list";
    }

    @GetMapping("/wardrobe/{id}/clothes/add")
    public String addClothOfWardrobe(Model model, @PathVariable(name = "id") Long wardrobeId) {
        ResponseForWardrobe responseForWardrobe = (ResponseForWardrobe) wardrobeService.findById(wardrobeId).getBody();
        model.addAttribute("wardrobe", responseForWardrobe);
        return "cloth/cloth-save";
    }

    @GetMapping("/records")
    public String findRecords(Model model) {
        model.addAttribute("records", recordService.findAll().getBody());
        return "cloth/record-list";
    }

    @GetMapping("/statistics")
    public String findStatistics(Model model) {
        ResponseForStatistics responseForStatistics = statisticsService.findStatistics(Optional.empty()).getBody();
        model.addAttribute("contents", responseForStatistics.getContent());
        return "statistics/statistics-list";
    }

    @GetMapping("/statistics/me")
    public String findStatisticsByLoginUser(Model model, @LoginUser SessionMember sessionMember) {
        ResponseForStatistics responseForStatistics = statisticsService.findStatistics(Optional.ofNullable(sessionMember)).getBody();
        model.addAttribute("contents", responseForStatistics.getContent());
        return "statistics/statistics-me-list";
    }

}
