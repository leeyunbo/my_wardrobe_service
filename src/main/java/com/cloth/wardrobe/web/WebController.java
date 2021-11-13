package com.cloth.wardrobe.web;

import com.cloth.wardrobe.config.auth.LoginUser;
import com.cloth.wardrobe.config.auth.dto.SessionMember;
import com.cloth.wardrobe.dto.clothes.*;
import com.cloth.wardrobe.dto.community.RequestForLike;
import com.cloth.wardrobe.dto.community.ResponseForComments;
import com.cloth.wardrobe.dto.records.ResponseForRecords;
import com.cloth.wardrobe.dto.statistics.ResponseForStatistics;
import com.cloth.wardrobe.dto.wardrobe.RequestForWardrobe;
import com.cloth.wardrobe.dto.wardrobe.ResponseForWardrobe;
import com.cloth.wardrobe.dto.wardrobe.ResponseForWardrobes;
import com.cloth.wardrobe.exception.BadRequestException;
import com.cloth.wardrobe.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;


/**
 * 웹 Front를 위한 컨트롤러
 * 웹 어플리케이션의 이동 및 서버로의 요청을 담당한다.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
public class WebController {

    private final RecordService recordService;
    private final PostService communityService;
    private final WardrobeService wardrobeService;
    private final ClothService clothService;
    private final StatisticsService statisticsService;

    //== Home 관련 ==//
    @GetMapping("/")
    public String home() {
        return "home";
    }

    //== Wardrobe 관련 ==//
    @GetMapping("/wardrobes/save")
    public String saveWardrobe() {
        return "wardrobe/wardrobes-save";
    }

    @GetMapping("/wardrobes")
    public String findWardrobes(Model model, @RequestParam(name = "page_number", required = false,  defaultValue = "1") int pageNumber, @RequestParam(name = "page_size", required = false, defaultValue = "10") int pageSize) {
        ResponseForWardrobes responseForWardrobes = wardrobeService.findAll(pageNumber, pageSize).getBody();
        model.addAttribute("wardrobes", responseForWardrobes.getContents());
        return "wardrobe/wardrobes-list";
    }

    @GetMapping("/wardrobes/{id}")
    public String findWardrobeById(Model model, @PathVariable(name = "id") Long postId, @RequestParam(name = "page_number", required = false,  defaultValue = "1") int pageNumber, @RequestParam(name = "page_size", required = false, defaultValue = "10") int pageSize, @LoginUser SessionMember sessionMember) {
        RequestForLike requestForIsLike = new RequestForLike(sessionMember.getEmail());
        Boolean isLikeUser = communityService.isLikeUsers(postId, requestForIsLike).getBody().getIsLike();

        ResponseForWardrobe responseForWardrobe = wardrobeService.findById(postId).getBody();
        ResponseForComments responseForComments = communityService.findCommentsByPostId(postId, pageNumber, pageSize).getBody();

        model.addAttribute("wardrobe", responseForWardrobe);
        model.addAttribute("comments", responseForComments.getContents());
        model.addAttribute("isLike", isLikeUser);
        return "wardrobe/wardrobe-detail-view";
    }

    @GetMapping("/member/wardrobe")
    public String findWardrobeByMember(Model model, @RequestParam(name = "page_number", required = false,  defaultValue = "1") int pageNumber, @RequestParam(name = "page_size", required = false, defaultValue = "10") int pageSize, @LoginUser SessionMember sessionMember) {
        try {
            RequestForWardrobe requestForWardrobe = new RequestForWardrobe(sessionMember.getEmail());
            ResponseForWardrobe responseForWardrobe = wardrobeService.findByMember(requestForWardrobe).getBody();
            ResponseForComments responseForComments = communityService.findCommentsByPostId(responseForWardrobe.getId(), pageNumber, pageSize).getBody();

            RequestForLike requestForIsLike = new RequestForLike(sessionMember.getEmail());
            Boolean isLikeUser = communityService.isLikeUsers(responseForWardrobe.getId(), requestForIsLike).getBody().getIsLike();

            model.addAttribute("wardrobe", responseForWardrobe);
            model.addAttribute("comments", responseForComments.getContents());
            model.addAttribute("isLike", isLikeUser);

            return "wardrobe/wardrobe-detail-view";
        }
        catch (BadRequestException e) {
            return "wardrobe/wardrobes-save";
        }
    }

    //== Cloth 관련 ==//
    @GetMapping("/clothes/{id}")
    public String findClothById(Model model, @PathVariable(name = "id") Long id, @RequestParam(name = "page_number", required = false,  defaultValue = "1") int pageNumber, @RequestParam(name = "page_size", required = false, defaultValue = "10") int pageSize, @LoginUser SessionMember sessionMember) {
        RequestForLike requestForIsLike = new RequestForLike(sessionMember.getEmail());
        boolean isLikeUser = communityService.isLikeUsers(id, requestForIsLike).getBody().getIsLike();

        ResponseForCloth responseForCloth = clothService.findById(id).getBody();
        ResponseForRecords responseForRecords = clothService.findRecordsByClothId(pageNumber, pageSize, id).getBody();

        model.addAttribute("cloth", responseForCloth);
        model.addAttribute("records", responseForRecords.getContents());
        model.addAttribute("isLike", isLikeUser);

        return "cloth/cloth-detail-view";
    }

    @GetMapping("/wardrobe/{id}/clothes")
    public String clothesOfWardrobe(Model model, @PathVariable(name = "id") Long wardrobeId, @RequestParam(name = "page_number", required = false,  defaultValue = "1") int pageNumber, @RequestParam(name = "page_size", required = false, defaultValue = "10") int pageSize) {
        ResponseForWardrobe responseForWardrobe = wardrobeService.findById(wardrobeId).getBody();
        ResponseForClothes responseForClothes = wardrobeService.findClothesByWardrobeId(pageNumber, pageSize, wardrobeId).getBody();

        model.addAttribute("wardrobe", responseForWardrobe);
        model.addAttribute("clothes", responseForClothes.getContents());

        return "cloth/cloth-list";
    }

    @GetMapping("/wardrobe/{id}/clothes/add")
    public String addClothOfWardrobe(Model model, @PathVariable(name = "id") Long wardrobeId) {
        ResponseForWardrobe responseForWardrobe = wardrobeService.findById(wardrobeId).getBody();
        model.addAttribute("wardrobe", responseForWardrobe);

        return "cloth/cloth-save";
    }

    @GetMapping("/records")
    public String findRecords(Model model, @RequestParam(name = "page_number", required = false,  defaultValue = "1") int pageNumber, @RequestParam(name = "page_size", required = false, defaultValue = "10") int pageSize) {
        model.addAttribute("records", recordService.findAll(pageNumber, pageSize).getBody().getContents());

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
