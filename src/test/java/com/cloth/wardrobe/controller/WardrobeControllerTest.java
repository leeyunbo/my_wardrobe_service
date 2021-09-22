package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.dto.community.RequestForComment;
import com.cloth.wardrobe.dto.community.ResponseForComments;
import com.cloth.wardrobe.entity.clothes.Cloth;
import com.cloth.wardrobe.entity.clothes.Wardrobe;
import com.cloth.wardrobe.entity.member.Member;
import com.cloth.wardrobe.dto.wardrobe.ResponseForWardrobe;
import com.cloth.wardrobe.exception.BadRequestException;
import com.cloth.wardrobe.repository.ImageRepository;
import com.cloth.wardrobe.dto.community.RequestForCommentSave;
import com.cloth.wardrobe.dto.wardrobe.RequestForWardrobeSave;
import com.cloth.wardrobe.entity.member.MemberRepository;
import com.cloth.wardrobe.repository.WardrobeRepository;
import com.cloth.wardrobe.service.ClothService;
import com.cloth.wardrobe.service.PostService;
import com.cloth.wardrobe.service.WardrobeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WardrobeControllerTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    WardrobeRepository wardrobeRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    PostService postService;

    @Autowired
    WardrobeService wardrobeService;

    @Autowired
    ClothService clothService;

    protected MockHttpSession session;
    protected MockHttpServletRequest request;


    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() {
        wardrobeRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles="USER") //인증된 모의 사용자 생성, ROLE_USER 권한을 가진 사용자가 요청하는 것과 동일한 효과를 가지게 됨
    public void Post_Wardrobe_테스트() throws Exception {
        RequestForWardrobeSave requestDto = RequestForWardrobeSave
                .builder()
                .name("name")
                .isPublic("false")
                .build();
        String url = "http://localhost:8080/api/v1/wardrobe";


        // when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        // then
        List<Wardrobe> all = wardrobeRepository.findAll();
        assertThat(all.get(0).getName()).isEqualTo("name");
        assertThat(all.get(0).getMember().getEmail()).isEqualTo("nodoyunbok@gmail");
    }

    @Test
    public void 옷장저장_페이징_불러오기() {
        // given
        String name = "테스트 옷장";
        String isPublic = "false";
        int likeCnt = 0;
        Member member = memberRepository.findById(1L).get();

        wardrobeRepository.save(Wardrobe.builder()
                .member(member)
                .isPublic("true")
                .name(name)
                .build());
        wardrobeRepository.save(Wardrobe.builder()
                .member(member)
                .isPublic(isPublic)
                .name(name)
                .build());
        wardrobeRepository.save(Wardrobe.builder()
                .member(member)
                .isPublic(isPublic)
                .name(name)
                .build());
        wardrobeRepository.save(Wardrobe.builder()
                .member(member)
                .isPublic(isPublic)
                .name(name)
                .build());
        wardrobeRepository.save(Wardrobe.builder()
                .member(member)
                .isPublic(isPublic)
                .name(name)
                .build());

        // when, Page는 무조건 size만큼 할당이 된다.
        Pageable pageable = PageRequest.of(0,2);
        Page<Wardrobe> wardrobes = wardrobeRepository.findAll(pageable);

        assertThat(
                wardrobes
                .get()
                .map(ResponseForWardrobe::new)
                .collect(Collectors.toList())
                .size())
                    .isEqualTo(1);
    }

    @Test
    public void 옷장_댓글달기_댓글삭제() {

        // given
        String name = "테스트 옷장";
        String isPublic = "false";
        String email = "nodoyunbok@gmail.com";

        Member member = memberRepository.findById(1L).orElseThrow(() -> new BadRequestException("잘못된 요청입니다."));

        Wardrobe wardrobe = wardrobeRepository.save(Wardrobe.builder()
                .member(member)
                .isPublic(isPublic)
                .name(name)
                .build());

        RequestForCommentSave requestForCommentSave = RequestForCommentSave
                .builder()
                .content("TEST 내용")
                .email(email)
                .build();

        postService.writeComment(wardrobe.getId(), requestForCommentSave);

        ResponseForComments comments = postService.findCommentsByPostId(wardrobe.getId(), 1, 10).getBody();
        assertThat(comments.getContents().size()).isEqualTo(2);

        RequestForComment requestForComment = new RequestForComment(email);
        postService.deleteComment(wardrobe.getId(), comments.getContents().get(0).getId(), requestForComment);

        comments = postService.findCommentsByPostId(wardrobe.getId(), 1, 10).getBody();
        assertThat(comments.getContents().size()).isEqualTo(1);
    }


    @Test
    public void 옷_저장_테스트() {
        String name = "테스트 옷장";
        Member member = memberRepository.findById(1L).get();

        Wardrobe wardrobe = wardrobeRepository.save(Wardrobe.builder()
                .member(member)
                .isPublic("true")
                .name(name)
                .build());

        Cloth cloth = new Cloth();
        wardrobe.addCloth(cloth);

        List<Cloth> cloths = (List<Cloth>) clothService.findAll(1, 10).getBody();
        assertThat(cloths.size()).isEqualTo(1);
    }

    @Test
    public void beanValidaiton() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        RequestForWardrobeSave requestForWardrobeSave = new RequestForWardrobeSave("", "true", "servers1@naver.com");

        Set<ConstraintViolation<RequestForWardrobeSave>> violationSet = validator.validate(requestForWardrobeSave);
        for (ConstraintViolation<RequestForWardrobeSave> wardrobeSaveRequestDtoConstraintViolation : violationSet) {
            System.out.println("wardrobeSaveRequestDtoConstraintViolation = " + wardrobeSaveRequestDtoConstraintViolation);
            System.out.println("wardrobeSaveRequestDtoConstraintViolation.getMessage() = " + wardrobeSaveRequestDtoConstraintViolation.getMessage());
        }
    }

}