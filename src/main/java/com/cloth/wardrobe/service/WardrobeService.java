package com.cloth.wardrobe.service;

import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.domain.community.Comment;
import com.cloth.wardrobe.domain.community.CommentRepository;
import com.cloth.wardrobe.domain.community.Like;
import com.cloth.wardrobe.domain.community.LikeRepository;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.member.MemberRepository;
import com.cloth.wardrobe.domain.s3.Image;
import com.cloth.wardrobe.dto.clothes.*;
import com.cloth.wardrobe.domain.clothes.WardrobeRepository;
import com.cloth.wardrobe.dto.community.CommentResponseRequestDto;
import com.cloth.wardrobe.dto.community.CommentSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class WardrobeService {

    private final MemberRepository memberRepository;
    private final WardrobeRepository wardrobeRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public Long save(WardrobeSaveRequestDto requestDto, Long memberId) {
        Image image = Image
                .builder()
                .imageS3Path(requestDto.getImage())
                .build();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new IllegalArgumentException("회원 정보가 잘못되었습니다."));

        requestDto.setMember(member);

        return wardrobeRepository.save(requestDto.toEntity(image)).getId();
    }

    @Transactional
    public Long update(Long id, WardrobeUpdateRequestDto requestDto) {
        Wardrobe wardrobe = wardrobeRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 옷장이 존재하지 않습니다. id=" + id));

        wardrobe.update(requestDto.getImage(), requestDto.getName(), requestDto.getIsPublic());

        return id;
    }


    /**
     * 특정 옷장의 정보를 가져온다.
     * @param id
     * @return
     */
    @Transactional
    public WardrobeGetRequestDto findById(Long id) {
        Wardrobe wardrobe = wardrobeRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 옷장이 존재하지 않습니다. id=" + id));

        return new WardrobeGetRequestDto(wardrobe);
    }

    /**
     * isPublic이 true인 옷장들의 리스트들을 유저 이름 검색 기준으로 가져온다. (페이징 사용)
     */
    @Transactional
    public Page<Wardrobe> findAll(Pageable pageable) {
        return wardrobeRepository.findAll(pageable);
    }


    /**
     * 좋아요 수를 증가시킨다.
     */
    @Transactional
    public Long addLikeCnt(Long id, Long memberId) {
        Wardrobe wardrobe = wardrobeRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 옷장이 존재하지 않습니다. id=" + id));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 멤버가 존재하지 않습니다. id=" + id));

        Like like = Like.builder()
                .member(member)
                .wardrobe(wardrobe)
                .build();

        wardrobe.addLikeCnt(like);

        return id;
    }

    /**
     * 좋아요 수를 감소시킨다.
     */
    @Transactional
    public Long delLikeCnt(Long id, Long memberId) {
        Wardrobe wardrobe = wardrobeRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 옷장이 존재하지 않습니다. id=" + id));

        Like like = likeRepository.findByMember_Id(memberId)
                .orElseThrow(() ->
                        new IllegalArgumentException("좋아요 오류"));

        wardrobe.delLikeCnt(like);

        return id;
    }

    /**
     * 댓글을 모두 가져온다.
     */
    @Transactional
    public List<CommentResponseRequestDto> getComments(Long id) {
        Wardrobe wardrobe = wardrobeRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 옷장이 존재하지 않습니다. id=" + id));

        return wardrobe.getComments().stream()
                .map(CommentResponseRequestDto::new)
                .collect(Collectors.toList());
    }


    /**
     * 댓글을 작성한다.
     */
    @Transactional
    public Long writeComment(Long id, Long memberId, CommentSaveRequestDto commentSaveRequestDto) {
        Wardrobe wardrobe = wardrobeRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 옷장이 존재하지 않습니다. id=" + id));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 멤버가 존재하지 않습니다. id=" + memberId));

        commentSaveRequestDto.setMember(member);

        wardrobe.writeComment(commentSaveRequestDto.toEntity());


        return id;
    }

    /**
     * 댓글을 삭제한다.
     */
    @Transactional
    public Long deleteComment(Long wardrobeId, Long commentId) {
        Wardrobe wardrobe = wardrobeRepository.findById(wardrobeId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 옷장이 존재하지 않습니다. id=" + wardrobeId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() ->
                        new IllegalArgumentException("댓글이 존재하지 않습니다. id=" + commentId));

        wardrobe.deleteComment(comment);

        return wardrobeId;
    }

}
