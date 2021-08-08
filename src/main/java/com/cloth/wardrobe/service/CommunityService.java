package com.cloth.wardrobe.service;

import com.cloth.wardrobe.domain.clothes.*;
import com.cloth.wardrobe.domain.community.*;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.member.MemberRepository;
import com.cloth.wardrobe.domain.community.Like;
import com.cloth.wardrobe.exception.DoNotClickedException;
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

    private final MemberRepository memberRepository;
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
        Like like;

        try {
            like = findLikeByMemberIdAndPostId(postId, member.getId(), type);
            post.changeLikeCnt(like, MethodType.DELETE);
        } catch (DoNotClickedException e) {
            like = createLikeByPostType(type, member, post);
            post.changeLikeCnt(like, MethodType.ADD);
        }

        return new ResponseEntity<>(like.getId(), HttpStatus.OK);
    }

    @Transactional
    public boolean isLikeUsers(Long postId, Long memberId, PostType type) {
        try {
            findLikeByMemberIdAndPostId(postId, memberId, type);
        }
        catch (DoNotClickedException e) {
            return false;
        }

        return true;
    }


    private Like createLikeByPostType(PostType type, Member member, Post post) {
        if(post instanceof Wardrobe) {
            return new Like(member, (Wardrobe) post);
        }
        else if(post instanceof Cloth) {
            return new Like(member, (Cloth) post);
        }
        else if(post instanceof Record) {
            return new Like(member, (Record) post);
        }

        return null;
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 멤버가 존재하지 않습니다. id=" + memberId));
    }

    private Post findPostById(Long postId, PostType type) {
        if(type.equals(PostType.Wardrobe)) {
            return wardrobeRepository.findById(postId)
                    .orElseThrow(() ->
                            new IllegalArgumentException("해당 옷장이 존재하지 않습니다. id=" + postId));
        }
        else if(type.equals(PostType.Cloth)) {
            return clothRepository.findById(postId)
                    .orElseThrow(() ->
                            new IllegalArgumentException("해당 옷이 존재하지 않습니다. id=" + postId));
        }
        else if(type.equals(PostType.Record)) {
            return recordRepository.findById(postId)
                    .orElseThrow(() ->
                            new IllegalArgumentException("해당 기록이 존재하지 않습니다. id=" + postId));
        }

        else return null;
    }

    private Like findLikeByMemberIdAndPostId(Long postId, Long memberId, PostType type) {
        if(type.equals(PostType.Wardrobe)) {
            return likeRepository.findByMember_IdAndWardrobe_Id(memberId, postId)
                    .orElseThrow(() ->
                            new DoNotClickedException("좋아요를 누르지 않았습니다. id=" + memberId));
        }
        else if(type.equals(PostType.Cloth)) {
            return likeRepository.findByMember_IdAndCloth_Id(memberId, postId)
                    .orElseThrow(() ->
                            new DoNotClickedException("좋아요를 누르지 않았습니다. id=" + memberId));
        }
        else if(type.equals(PostType.Record)) {
            return likeRepository.findByMember_IdAndRecord_Id(memberId, postId)
                    .orElseThrow(() ->
                            new DoNotClickedException("좋아요를 누르지 않았습니다. id=" + memberId));
        }

        else return null;
    }
}
