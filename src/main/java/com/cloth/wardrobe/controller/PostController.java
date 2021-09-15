package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.config.auth.CustomOAuth2MemberService;
import com.cloth.wardrobe.config.auth.LoginUser;
import com.cloth.wardrobe.config.auth.dto.SessionMember;
import com.cloth.wardrobe.entity.community.PostType;
import com.cloth.wardrobe.dto.community.ResponseForComments;
import com.cloth.wardrobe.dto.community.ResponseForLike;
import com.cloth.wardrobe.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService communityService;
    private final CustomOAuth2MemberService customOAuth2MemberService;

    @GetMapping("/{id}/is_like")
    public ResponseEntity<ResponseForLike> isLikeUsers(@PathVariable Long id, @LoginUser SessionMember sessionMember) {
        return communityService.isLikeUsers(id, customOAuth2MemberService.getMemberBySession(sessionMember).getId());
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<ResponseForComments> findCommentsByPostId(@PathVariable Long id, @RequestParam("page_number") int pageNumber, @RequestParam("page_size") int pageSize) {
        return communityService.findCommentsByPostId(id, pageNumber, pageSize);
    }

    @PutMapping("/{id}/like_cnt")
    public ResponseEntity<?> changeLikeCnt(@PathVariable Long id, @LoginUser SessionMember sessionMember) {
        return communityService.changeLikeCnt(id,
                customOAuth2MemberService.getMemberBySession(sessionMember));
    }

}
