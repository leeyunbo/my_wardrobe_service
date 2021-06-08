package com.cloth.wardrobe.controller;

import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.dto.clothes.WardrobeSaveRequestDto;
import com.cloth.wardrobe.domain.member.MemberRepository;
import com.cloth.wardrobe.domain.clothes.WardrobeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


// Spring이랑 엮어서 실행할래
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WardrobeControllerTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    WardrobeRepository wardrobeRepository;

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
        WardrobeSaveRequestDto requestDto = WardrobeSaveRequestDto
                .builder()
                .name("name")
                .member(memberRepository.findById(1L).get())
                .isPublic(false)
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
    public void 옷장저장_불러오기() {
        // given
        String name = "테스트 옷장";
        boolean isPublic = false;
        int likeCnt = 0;
        Member member = memberRepository.findById(1L).get();

        wardrobeRepository.save(Wardrobe.builder()
                                .member(member)
                                .isPublic(isPublic)
                                .likeCnt(likeCnt)
                                .name(name)
                                .build());

        // when
        List<Wardrobe> wardrobes = wardrobeRepository.findAll();

        // then
        Wardrobe wardrobe = wardrobes.get(0);
        assertThat(wardrobe.getName()).isEqualTo(name);
        assertThat(wardrobe.getLikeCnt()).isEqualTo(0);

    }

}