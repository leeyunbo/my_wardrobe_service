package com.cloth.wardrobe.service;

import com.cloth.wardrobe.domain.clothes.*;
import com.cloth.wardrobe.domain.community.*;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.member.MemberRepository;
import com.cloth.wardrobe.domain.community.Like;
import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.exception.BadRequestException;
import com.cloth.wardrobe.exception.WrongAccessException;
import com.cloth.wardrobe.repository.ClothRepository;
import com.cloth.wardrobe.repository.LikeRepository;
import com.cloth.wardrobe.repository.RecordRepository;
import com.cloth.wardrobe.repository.WardrobeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Slf4j
public class CommunityService {

    private final WardrobeRepository wardrobeRepository;
    private final ClothRepository clothRepository;
    private final RecordRepository recordRepository;
    private final LikeRepository likeRepository;

    /**
     * 좋아요 수를 증가시키거나 감소시킨다.
     */
    @Transactional
    public ResponseEntity<?> changeLikeCnt(Long postId, Member member, PostType type) {
        Post post = findPostById(postId, type);
        Like like = findLikeByMemberIdAndPostId(postId, member.getId(), type);

        if(like == null) post.changeLikeCnt(new Like(member).setPost(post), MethodType.ADD);
        else post.changeLikeCnt(like, MethodType.DELETE);

        Response response = new Response();
        response.set_code(200);
        response.set_message("OK");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Transactional
    public boolean isLikeUsers(Long postId, Long memberId, PostType type) {
        return findLikeByMemberIdAndPostId(postId, memberId, type) != null;
    }

    private Post findPostById(Long postId, PostType type) {
        if(type.equals(PostType.Wardrobe)) {
            return wardrobeRepository.findById(postId)
                    .orElseThrow(() ->
                            new BadRequestException("해당 옷장이 존재하지 않습니다. id=" + postId));
        }
        else if(type.equals(PostType.Cloth)) {
            return clothRepository.findById(postId)
                    .orElseThrow(() ->
                            new BadRequestException("해당 옷이 존재하지 않습니다. id=" + postId));
        }
        else {
            return recordRepository.findById(postId)
                    .orElseThrow(() ->
                            new BadRequestException("해당 기록이 존재하지 않습니다. id=" + postId));
        }
    }

    private Like findLikeByMemberIdAndPostId(Long postId, Long memberId, PostType type) {
        if(type.equals(PostType.Wardrobe)) return likeRepository.findByMember_IdAndWardrobe_Id(memberId, postId);
        else if(type.equals(PostType.Cloth)) return likeRepository.findByMember_IdAndCloth_Id(memberId, postId);
        else  return likeRepository.findByMember_IdAndRecord_Id(memberId, postId);
    }
}
