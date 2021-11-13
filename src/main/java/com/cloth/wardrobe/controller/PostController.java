package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.config.auth.LoginUser;
import com.cloth.wardrobe.config.auth.dto.SessionMember;
import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.dto.community.*;
import com.cloth.wardrobe.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}/is_like")
    public ResponseEntity<ResponseForLike> isLikeUsers(@PathVariable Long id, @RequestBody RequestForLike requestForLike) {
        return postService.isLikeUsers(id, requestForLike);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<ResponseForComments> findCommentsByPostId(@PathVariable Long id, @RequestParam("page_number") int pageNumber, @RequestParam("page_size") int pageSize) {
        return postService.findCommentsByPostId(id, pageNumber, pageSize);
    }

    @PutMapping("/{id}/like")
    public ResponseEntity<Response> changeLikeCnt(@PathVariable Long id, @RequestBody RequestForLike requestForLike) {
        return postService.changeLikeCnt(id, requestForLike);
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<Response> writeComment(@PathVariable Long id,
                                                 @RequestBody RequestForCommentSave requestForCommentSave,
                                                 @LoginUser SessionMember sessionMember) {
        return postService.writeComment(id, requestForCommentSave, sessionMember);
    }

    @DeleteMapping("/{post_id}/comment/{comment_id}")
    public ResponseEntity<Response> deleteComment(@PathVariable(name="post_id") Long postId, @PathVariable(name="comment_id") Long commentId, @RequestBody RequestForComment requestForComment) {
        return postService.deleteComment(postId, commentId, requestForComment);
    }

    @PostMapping("{id}/comment/test")
    public ResponseEntity<Response> addTestData(@PathVariable Long id,
                                                  @RequestBody RequestForCommentSave requestForCommentSave,
                                                  @LoginUser SessionMember sessionMember) {
        return postService.writeComments(id, requestForCommentSave, sessionMember);
    }
}
