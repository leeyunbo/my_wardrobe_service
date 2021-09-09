package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.config.auth.CustomOAuth2MemberService;
import com.cloth.wardrobe.config.auth.LoginUser;
import com.cloth.wardrobe.config.auth.dto.SessionMember;
import com.cloth.wardrobe.domain.community.PostType;
import com.cloth.wardrobe.dto.community.ResponseForComments;
import com.cloth.wardrobe.dto.community.ResponseForLike;
import com.cloth.wardrobe.service.CommunityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;
    private final CustomOAuth2MemberService customOAuth2MemberService;

    @GetMapping("api/v1/{type}/{postId}/islike")
    public ResponseEntity<ResponseForLike> isLikeUsers(@RequestParam(name="type") String type, @RequestParam(name="postId") Long postId, @LoginUser SessionMember sessionMember) {
        return communityService.isLikeUsers(postId, customOAuth2MemberService.getMemberBySession(sessionMember).getId(), PostType.valueOf(type));
    }

    @GetMapping("api/v1/{type}/{postId}/comments")
    public ResponseEntity<ResponseForComments> findCommentsByPostId(@PathVariable(name="type") String type, @RequestParam(name="postId") Long postId, @LoginUser SessionMember sessionMember) {
        return communityService.findCommentsByPostId(postId, type);
    }

}
