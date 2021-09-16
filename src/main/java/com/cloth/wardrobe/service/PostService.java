package com.cloth.wardrobe.service;

import com.cloth.wardrobe.config.auth.dto.SessionMember;
import com.cloth.wardrobe.dto.community.RequestForCommentSave;
import com.cloth.wardrobe.entity.clothes.*;
import com.cloth.wardrobe.entity.community.*;
import com.cloth.wardrobe.entity.member.Member;
import com.cloth.wardrobe.entity.community.Like;
import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.dto.community.ResponseForComments;
import com.cloth.wardrobe.dto.community.ResponseForLike;
import com.cloth.wardrobe.dto.community.element.ContentForComment;
import com.cloth.wardrobe.exception.BadRequestException;
import com.cloth.wardrobe.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
@Slf4j
public class PostService {

    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final PaginationService paginationService;
    private final CheckService checkService;

    /**
     * 좋아요 수를 증가시키거나 감소시킨다.
     */
    @Transactional
    public ResponseEntity<?> changeLikeCnt(Long id, Member member) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("잘못된 요청 입니다."));
        Optional<Like> like = likeRepository.findByMember_IdAndPost_Id(member.getId(), id);

        if(like.isEmpty()) {
            post.changeLikeCnt(new Like(member, post), MethodType.ADD);
        }
        else {
            post.changeLikeCnt(like.get(), MethodType.DELETE);
        }

        Response response = new Response();
        response.set_code(200);
        response.set_message("OK");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ResponseForLike> isLikeUsers(Long postId, Long memberId) {
        Optional<Like> like = likeRepository.findByMember_IdAndPost_Id(memberId, postId);
        ResponseForLike response;

        if(like.isEmpty()) {
            response = new ResponseForLike(false);
        }
        else {
            response = new ResponseForLike(like.get(), true);
        }

        response.set_code(200);
        response.set_message("OK");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ResponseForComments> findCommentsByPostId(Long postId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);
        Page<Comment> paginatedComments = commentRepository.findCommentsByPostId(pageRequest, postId);

        return paginationService.convertToPaginatedComments(paginatedComments);
    }

    @Transactional
    public ResponseEntity<?> writeComment(Long postId, SessionMember sessionMember, RequestForCommentSave commentSaveRequestDto) {
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new BadRequestException("잘못된 요청 입니다."));

        checkService.confirmRightApproach(sessionMember.getEmail(), post.getMember().getEmail());

        post.writeComment(commentSaveRequestDto.toEntity());

        Response response = new Response();
        response.set_code(200);
        response.set_message("OK");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 댓글을 삭제한다.
     */
    @Transactional
    public ResponseEntity<?> deleteComment(Long postId, Long commentId, SessionMember sessionMember) {
        PostEntity post = postRepository.findById(postId).orElseThrow(() -> new BadRequestException("잘못된 요청 입니다."));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new BadRequestException("잘못된 요청 입니다."));

        checkService.confirmRightApproach(sessionMember.getEmail(), post.getMember().getEmail());

        post.deleteComment(comment);

        Response response = new Response();
        response.set_code(200);
        response.set_message("OK");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
