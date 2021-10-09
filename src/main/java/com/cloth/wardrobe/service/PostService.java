package com.cloth.wardrobe.service;

import com.cloth.wardrobe.dto.community.*;
import com.cloth.wardrobe.entity.clothes.*;
import com.cloth.wardrobe.entity.community.*;
import com.cloth.wardrobe.entity.member.Member;
import com.cloth.wardrobe.entity.community.Like;
import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.entity.member.MemberRepository;
import com.cloth.wardrobe.exception.BadRequestException;
import com.cloth.wardrobe.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Slf4j
public class PostService {

    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final PaginationService paginationService;
    private final CheckService checkService;

    /**
     * 좋아요 수를 증가시키거나 감소시킨다.
     */
    @Transactional
    public ResponseEntity<Response> changeLikeCnt(Long id, RequestForLike requestForLike) {
        Member member = memberRepository.findByEmail(requestForLike.getEmail())
                .orElseThrow(() -> new BadRequestException("잘못된 요청 입니다."));
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

    public ResponseEntity<ResponseForLike> isLikeUsers(Long postId, RequestForLike requestForLike) {
        Member member = memberRepository.findByEmail(requestForLike.getEmail())
                .orElseThrow(() -> new BadRequestException("잘못된 요청 입니다."));

        Optional<Like> like = likeRepository.findByMember_IdAndPost_Id(member.getId(), postId);

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

    public ResponseEntity<ResponseForComments> findCommentsByPostId(Long postId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);
        Page<Comment> paginatedComments = commentRepository.findCommentsByPostId(pageRequest, postId);

        return paginationService.convertToPaginatedComments(paginatedComments);
    }

    @Transactional
    public ResponseEntity<Response> writeComment(Long postId, RequestForCommentSave requestForCommentSave) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new BadRequestException("잘못된 요청 입니다."));
        Member member = memberRepository.findByEmail(requestForCommentSave.getEmail())
                .orElseThrow(() -> new BadRequestException("잘못된 요청 입니다."));

        post.writeComment(requestForCommentSave.toEntity(member));

        Response response = new Response();
        response.set_code(200);
        response.set_message("OK");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 댓글을 삭제한다.
     */
    @Transactional
    public ResponseEntity<Response> deleteComment(Long postId, Long commentId, RequestForComment requestForComment) {
        log.debug("--------------------------------------------------------------------------------------" + requestForComment.getEmail() + "--------------------------------------------------------------------------------------");
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new BadRequestException("잘못된 요청 입니다."));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BadRequestException("잘못된 요청 입니다."));
        Member member = memberRepository.findByEmail(requestForComment.getEmail())
                .orElseThrow(() -> new BadRequestException("잘못된 요청 입니다."));

        checkService.isAppropriateEmail(member.getEmail(), comment.getMember().getEmail());

        post.deleteComment(comment);

        Response response = new Response();
        response.set_code(200);
        response.set_message("OK");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
