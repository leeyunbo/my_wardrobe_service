package com.cloth.wardrobe.service;

import com.cloth.wardrobe.domain.clothes.Cloth;
import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.domain.community.Comment;
import com.cloth.wardrobe.domain.common.Image;
import com.cloth.wardrobe.dto.clothes.element.ContentForWardrobe;
import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.exception.BadRequestException;
import com.cloth.wardrobe.exception.DoNotFoundContentException;
import com.cloth.wardrobe.repository.ClothRepository;
import com.cloth.wardrobe.repository.CommentRepository;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.member.MemberRepository;
import com.cloth.wardrobe.dto.clothes.*;
import com.cloth.wardrobe.repository.WardrobeRepository;
import com.cloth.wardrobe.dto.community.ResponseForComment;
import com.cloth.wardrobe.dto.community.RequestForCommentSave;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class WardrobeService {

    private final MemberRepository memberRepository;
    private final WardrobeRepository wardrobeRepository;
    private final CommentRepository commentRepository;
    private final ClothRepository clothRepository;
    private final PaginationService paginationService;

    /**
     * 옷장의 정보를 저장한다.
     */
    @Transactional
    public ResponseEntity<?> save(RequestForWardrobeSave requestForWardrobeSave, Member member, MultipartFile file) {
        try {
            Image image = new Image().fileUpload(file, member.getEmail());
            Wardrobe wardrobe = requestForWardrobeSave.toEntity();
            wardrobe.setImage(image);
            wardrobe.setMember(member);
            wardrobeRepository.save(requestForWardrobeSave.toEntity());
        } catch (IOException e) {
            throw new BadRequestException("파일이 손상되었습니다.");
        }

        Response response = new Response();
        response.set_code(200);
        response.set_message("OK");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 옷장의 정보를 수정한다.
     */
    @Transactional
    public ResponseEntity<?> update(Long wardrobeId, RequestForWardrobeUpdate requestDto) {
        Wardrobe wardrobe = wardrobeRepository.findById(wardrobeId)
                .orElseThrow(() ->
                        new BadRequestException("해당 옷장이 존재하지 않습니다. id=" + wardrobeId));
        wardrobe.update(requestDto.getImage(), requestDto.getName(), requestDto.getIsPublic());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * 특정 옷장의 정보를 가져온다.
     */
    @Transactional
    public ResponseEntity<?> findById(Long wardrobeId) {
        Wardrobe wardrobe = wardrobeRepository.findById(wardrobeId)
                .orElseThrow(() ->
                        new BadRequestException("해당 옷장이 존재하지 않습니다. id=" + wardrobeId));
        return new ResponseEntity<>(new ResponseForWardrobe(wardrobe), HttpStatus.OK);
    }

    /**
     * 로그인한 유저의 옷장 정보를 가져온다.
     */
    @Transactional
    public ResponseEntity<ResponseForWardrobe> findByMember(Member member) {
        Wardrobe wardrobe = wardrobeRepository.findWardrobeByMember(member)
                .orElseThrow(() -> new DoNotFoundContentException("해당 멤버의 옷장이 존재하지 않습니다. id=" + member.getId()));

        ResponseForWardrobe responseForWardrobe = new ResponseForWardrobe(wardrobe);
        responseForWardrobe.set_code(200);
        responseForWardrobe.set_message("OK");
        return new ResponseEntity<>(responseForWardrobe, HttpStatus.OK);
    }

    /**
     * isPublic이 true인 옷장들의 리스트들을 유저 이름 검색 기준으로 가져온다. (페이징 사용)
     */
    @Transactional
    public ResponseEntity<?> findAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);
        Page<Wardrobe> paginationedWardrobes = wardrobeRepository.findAll(pageRequest);

        return paginationService.convertToPaginatedWardrobes(paginationedWardrobes);
    }

    /**
     * 댓글을 모두 가져온다.
     */
    @Transactional
    public List<ResponseForComment> getComments(Long wardrobeId) {
        Wardrobe wardrobe = wardrobeRepository.findById(wardrobeId).orElseThrow(() -> new IllegalArgumentException("해당 옷장이 존재하지 않습니다. id=" + wardrobeId));

        return wardrobe.getComments()
                .stream()
                .map(ResponseForComment::new)
                .collect(Collectors.toList());
    }


    /**
     * 댓글을 작성한다.
     */
    @Transactional
    public ResponseEntity<?> writeComment(Long wardrobeId, Long memberId, RequestForCommentSave commentSaveRequestDto) {
        Wardrobe wardrobe =  wardrobeRepository.findById(wardrobeId).orElseThrow(() -> new BadRequestException("해당 옷장이 존재하지 않습니다. id=" + wardrobeId));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new BadRequestException("해당 멤버가 존재하지 않습니다. id=" + memberId));

        commentSaveRequestDto.setMember(member);
        wardrobe.writeComment(commentSaveRequestDto.toEntity());

        Response response = new Response();
        response.set_code(200);
        response.set_message("OK");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 댓글을 삭제한다.
     */
    @Transactional
    public ResponseEntity<?> deleteComment(Long wardrobeId, Long commentId, Member member) {
        Wardrobe wardrobe = wardrobeRepository.findById(wardrobeId).orElseThrow(() -> new BadRequestException("해당 옷장이 존재하지 않습니다. id=" + wardrobeId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new BadRequestException("댓글이 존재하지 않습니다. id=" + commentId));

        if (!comment.getMember().getEmail().equals(member.getEmail())) throw new BadRequestException("올바르지 않은 접근입니다.");

        wardrobe.deleteComment(comment);

        Response response = new Response();
        response.set_code(200);
        response.set_message("OK");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 옷을 추가한다.
     */
    @Transactional
    public ResponseEntity<?> addCloth(Long wardrobeId, RequestForClothSave requestForClothSave, Member member, MultipartFile file) {
        try {
            Wardrobe wardrobe = wardrobeRepository.findById(wardrobeId).orElseThrow(() -> new BadRequestException("해당 옷장이 존재하지 않습니다. id=" + wardrobeId));
            Image image = new Image().fileUpload(file, member.getEmail());

            if (!wardrobe.getMember().getEmail().equals(member.getEmail())) throw new BadRequestException("올바르지 않은 접근입니다.");

            requestForClothSave.setMember(member);
            requestForClothSave.setImage(image);
            wardrobe.addCloth(requestForClothSave.toEntity());
        }
        catch (IOException e) {
            throw new BadRequestException("파일이 손상되었습니다.");
        }

        Response response = new Response();
        response.set_code(200);
        response.set_message("OK");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 옷을 삭제한다.
     */
    @Transactional
    public ResponseEntity<?> deleteCloth(Long wardrobeId, Long clothId, Member member) {
        Wardrobe wardrobe = wardrobeRepository.findById(wardrobeId).orElseThrow(() -> new BadRequestException("해당 옷장이 존재하지 않습니다. id=" + wardrobeId));
        Cloth cloth = clothRepository.findById(clothId).orElseThrow(() -> new BadRequestException("해당 품목이 존재하지 않습니다. id=" + clothId));

        if(!wardrobe.getMember().getEmail().equals(member.getEmail())) throw new BadRequestException("올바르지 않은 접근입니다.");

        wardrobe.deleteCloth(cloth);

        Response response = new Response();
        response.set_code(200);
        response.set_message("OK");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
