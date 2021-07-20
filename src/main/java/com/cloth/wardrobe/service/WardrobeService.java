package com.cloth.wardrobe.service;

import com.cloth.wardrobe.config.auth.CustomOAuth2MemberService;
import com.cloth.wardrobe.config.auth.dto.SessionMember;
import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.domain.community.Comment;
import com.cloth.wardrobe.repository.ClothRepository;
import com.cloth.wardrobe.repository.CommentRepository;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.member.MemberRepository;
import com.cloth.wardrobe.dto.clothes.*;
import com.cloth.wardrobe.repository.WardrobeRepository;
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

    private final CustomOAuth2MemberService customOAuth2MemberService;
    private final MemberRepository memberRepository;
    private final WardrobeRepository wardrobeRepository;
    private final CommentRepository commentRepository;
    private final ClothRepository clothRepository;

    /**
     * 옷장의 정보를 저장한다.
     * @param wardrobeSaveRequestDto
     * @param memberId
     * @return
     */
    @Transactional
    public Long save(WardrobeSaveRequestDto wardrobeSaveRequestDto, Long memberId) {
        Member member = findMemberById(memberId);

        wardrobeSaveRequestDto.setMember(member);

        return wardrobeRepository.save((Wardrobe) wardrobeSaveRequestDto.toEntity()).getId();
    }

    /**
     * 옷장의 정보를 수정한다.
     * @param wardrobeId
     * @param requestDto
     * @return
     */
    @Transactional
    public Long update(Long wardrobeId, WardrobeUpdateRequestDto requestDto) {
        Wardrobe wardrobe = findWardrobeById(wardrobeId);

        wardrobe.update(requestDto.getImage(), requestDto.getName(), requestDto.getIsPublic());

        return wardrobeId;
    }


    /**
     * 특정 옷장의 정보를 가져온다.
     * @param wardrobeId
     * @return
     */
    @Transactional
    public WardrobeGetRequestDto findById(Long wardrobeId) {
        Wardrobe wardrobe = findWardrobeById(wardrobeId);

        return new WardrobeGetRequestDto(wardrobe);
    }

    /**
     * 로그인한 유저의 옷장 정보를 가져온다.
     * @param sessionMember
     * @return
     */
    @Transactional
    public WardrobeGetRequestDto findByMember(SessionMember sessionMember) {
        Member member = customOAuth2MemberService.getMemberBySession(sessionMember);

        Wardrobe wardrobe = wardrobeRepository.findWardrobeByMember(member)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 멤버의 옷장 존재하지 않습니다. id=" + member.getId()));

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
     * 댓글을 모두 가져온다.
     */
    @Transactional
    public List<CommentResponseRequestDto> getComments(Long wardrobeId) {
        Wardrobe wardrobe = findWardrobeById(wardrobeId);

        return wardrobe.getComments().stream()
                .map(CommentResponseRequestDto::new)
                .collect(Collectors.toList());
    }


    /**
     * 댓글을 작성한다.
     */
    @Transactional
    public Long writeComment(Long wardrobeId, Long memberId, CommentSaveRequestDto commentSaveRequestDto) {
        Wardrobe wardrobe = findWardrobeById(wardrobeId);
        Member member = findMemberById(memberId);

        commentSaveRequestDto.setMember(member);
        wardrobe.writeComment(commentSaveRequestDto.toEntity());

        return wardrobeId;
    }

    /**
     * 댓글을 삭제한다.
     */
    @Transactional
    public Long deleteComment(Long wardrobeId, Long commentId, Member member) {
        Wardrobe wardrobe = findWardrobeById(wardrobeId);
        Comment comment = findCommentById(commentId);

        if(!comment.getMember().getEmail().equals(member.getEmail())) {
            return -1L;
        }

        wardrobe.deleteComment(comment);

        return wardrobeId;
    }

    /**
     * 옷을 추가한다.
     */
    @Transactional
    public Long addCloth(Long wardrobeId, ClothSaveRequestDto clothSaveRequestDto, Member member) {
        Wardrobe wardrobe = findWardrobeById(wardrobeId);

        if(!wardrobe.getMember().getEmail().equals(member.getEmail())) {
            return -1L;
        }

        clothSaveRequestDto.setMember(member);
        wardrobe.addCloth(clothSaveRequestDto.toEntity());

        return wardrobeId;
    }

    /**
     * 옷을 삭제한다.
     */
    @Transactional
    public Long deleteCloth(Long wardrobeId, Long clothId, Member member) {
        Wardrobe wardrobe = findWardrobeById(wardrobeId);
        Cloth cloth = findClothById(clothId);

        if(!wardrobe.getMember().getEmail().equals(member.getEmail())) {
            return -1L;
        }

        wardrobe.deleteCloth(cloth);

        return wardrobeId;
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 멤버가 존재하지 않습니다. id=" + memberId));
    }

    private Wardrobe findWardrobeById(Long wardrobeId) {
        return wardrobeRepository.findById(wardrobeId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 옷장이 존재하지 않습니다. id=" + wardrobeId));
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() ->
                        new IllegalArgumentException("댓글이 존재하지 않습니다. id=" + commentId));
    }

    private Cloth findClothById(Long clothId) {
        return clothRepository.findById(clothId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 품목이 존재하지 않습니다. id=" + clothId));
    }
}
