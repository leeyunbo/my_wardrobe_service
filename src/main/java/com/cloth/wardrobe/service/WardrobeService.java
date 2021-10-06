package com.cloth.wardrobe.service;

import com.cloth.wardrobe.config.auth.dto.RequestForMember;
import com.cloth.wardrobe.dto.common.ResponseMessage;
import com.cloth.wardrobe.dto.wardrobe.*;
import com.cloth.wardrobe.entity.clothes.Cloth;
import com.cloth.wardrobe.entity.clothes.Wardrobe;
import com.cloth.wardrobe.entity.common.Image;
import com.cloth.wardrobe.dto.common.Response;
import com.cloth.wardrobe.exception.BadRequestException;
import com.cloth.wardrobe.repository.ClothRepository;
import com.cloth.wardrobe.entity.member.Member;
import com.cloth.wardrobe.entity.member.MemberRepository;
import com.cloth.wardrobe.dto.clothes.*;
import com.cloth.wardrobe.repository.WardrobeRepository;
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

@RequiredArgsConstructor
@Service
@Slf4j
public class WardrobeService {

    private final MemberRepository memberRepository;
    private final WardrobeRepository wardrobeRepository;
    private final ClothRepository clothRepository;
    private final CheckService checkService;
    private final PaginationService paginationService;

    /**
     * 옷장의 정보를 저장한다.
     */
    @Transactional
    public ResponseEntity<Response> save(RequestForWardrobeSave requestForWardrobeSave, RequestForMember requestForMember, MultipartFile file) {
        try {
            Member member = memberRepository.findByEmail(requestForMember.getEmail())
                    .orElseThrow(() -> new BadRequestException(ResponseMessage.INVALID_PARAMETER));

            Image image = new Image().fileUpload(file, member.getEmail());
            Wardrobe wardrobe = requestForWardrobeSave.toEntity(member, image);
            wardrobeRepository.save(wardrobe);
        } catch (IOException e) {
            throw new BadRequestException(ResponseMessage.INTERNAL_SERVER_ERROR);
        }

        Response response = new Response();
        response.set_code(200);
        response.set_message("OK");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 옷장의 정보를 수정한다.
     */
    @Transactional
    public ResponseEntity<Response> update(Long wardrobeId, RequestForWardrobeUpdate requestForWardrobeUpdate, RequestForMember requestForMember) {
        Wardrobe wardrobe = wardrobeRepository.findById(wardrobeId)
                .orElseThrow(() -> new BadRequestException(ResponseMessage.INVALID_PARAMETER));

        checkService.isAppropriateEmail(requestForMember.getEmail(), wardrobe.getMember().getEmail());

        wardrobe.update(requestForWardrobeUpdate.getImage(), requestForWardrobeUpdate.getName(), requestForWardrobeUpdate.getIsPublic());

        Response response = new Response();
        response.set_message("OK");
        response.set_code(200);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * 특정 옷장의 정보를 가져온다.
     */
    @Transactional
    public ResponseEntity<ResponseForWardrobe> findById(Long wardrobeId) {
        Wardrobe wardrobe = wardrobeRepository.findById(wardrobeId)
                .orElseThrow(() -> new BadRequestException(ResponseMessage.INVALID_PARAMETER));

        ResponseForWardrobe responseForWardrobe = new ResponseForWardrobe(wardrobe);
        responseForWardrobe.set_code(200);
        responseForWardrobe.set_message("OK");

        return new ResponseEntity<>(new ResponseForWardrobe(wardrobe), HttpStatus.OK);
    }

    /**
     * 로그인한 유저의 옷장 정보를 가져온다.
     */
    @Transactional
    public ResponseEntity<ResponseForWardrobe> findByMember(RequestForWardrobe requestForWardrobe) {
        Member member = memberRepository.findByEmail(requestForWardrobe.getEmail())
                .orElseThrow(() -> new BadRequestException(ResponseMessage.INVALID_PARAMETER));
        Wardrobe wardrobe = wardrobeRepository.findWardrobeByMember(member)
                .orElseThrow(() -> new BadRequestException(ResponseMessage.INVALID_PARAMETER));

        ResponseForWardrobe responseForWardrobe = new ResponseForWardrobe(wardrobe);
        responseForWardrobe.set_code(200);
        responseForWardrobe.set_message("OK");

        return new ResponseEntity<>(responseForWardrobe, HttpStatus.OK);
    }

    /**
     * isPublic이 true인 옷장들의 리스트들을 유저 이름 검색 기준으로 가져온다. (페이징 사용)
     */
    @Transactional
    public ResponseEntity<ResponseForWardrobes> findAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);
        Page<Wardrobe> paginationedWardrobes = wardrobeRepository.findAll(pageRequest);

        return paginationService.convertToPaginatedWardrobes(paginationedWardrobes);
    }

    /**
     * 옷을 추가한다.
     */
    @Transactional
    public ResponseEntity<Response> addCloth(Long wardrobeId, RequestForClothSave requestForClothSave, RequestForMember requestForMember, MultipartFile file) {
        try {
            Member member = memberRepository.findByEmail(requestForMember.getEmail())
                    .orElseThrow(() -> new BadRequestException(ResponseMessage.INVALID_PARAMETER));
            Wardrobe wardrobe = wardrobeRepository.findById(wardrobeId)
                    .orElseThrow(() -> new BadRequestException(ResponseMessage.INVALID_PARAMETER));

            checkService.isAppropriateEmail(requestForMember.getEmail(), wardrobe.getMember().getEmail());

            Image image = new Image().fileUpload(file, requestForMember.getEmail());
            requestForClothSave.setImage(image);
            wardrobe.addCloth(requestForClothSave.toEntity(member));
        }
        catch (IOException e) {
            throw new BadRequestException(ResponseMessage.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<Response> deleteCloth(Long wardrobeId, Long clothId, RequestForMember requestForMember) {
        Wardrobe wardrobe = wardrobeRepository.findById(wardrobeId)
                .orElseThrow(() -> new BadRequestException(ResponseMessage.INVALID_PARAMETER));
        Cloth cloth = clothRepository.findById(clothId)
                .orElseThrow(() -> new BadRequestException(ResponseMessage.INVALID_PARAMETER));

        checkService.isAppropriateEmail(requestForMember.getEmail(), cloth.getMember().getEmail());

        wardrobe.deleteCloth(cloth);

        Response response = new Response();
        response.set_code(200);
        response.set_message("OK");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 옷장에 포함되는 옷을 가져온다.
     */
    public ResponseEntity<ResponseForClothes> findClothesByWardrobeId(int pageNumber, int pageSize, Long id) {
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize);
        Page<Cloth> paginatedClothes = clothRepository.findClothsByWardrobeId(pageRequest, id);

        return paginationService.convertToPaginatedClothes(paginatedClothes);
    }
}
