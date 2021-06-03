package com.cloth.wardrobe.config.auth;

import com.cloth.wardrobe.config.auth.dto.OAuthAttributes;
import com.cloth.wardrobe.config.auth.dto.SessionMember;
import com.cloth.wardrobe.domain.member.Member;
import com.cloth.wardrobe.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/**
 * 구글 로그인 후 가져온 사용자 정보를 기반으로 가입 및 정보수정, 세션 저장 기능 지원
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2MemberService implements OAuth2UserService {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;

    /**
     * Google 로그인을 통해 유저 정보를 가져온다.
     * @param userRequest
     * @return
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 로그인 서비스 코드 구분 (후에 네이버, 카카오 로그인 서비스 추가 대비)
        String userNameAttributeName = userRequest
                .getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName(); // OAuth2 로그인 진행 시 키가 되는 필드 값, 네이버 로그인/ 구글 로그인 동시 지원 시 사용됨

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes()); //OAuth2User의 attribute를 담는 DTO

        Member member = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionMember(member)); // 세션 DTO

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getAuthorityKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    /**
     * Member 영속성을 활용한 저장 및 수정
     * 구글 Picture나 이름이 변경되었을 수도 있으니 바로 바로 적용하도록 한다.
     * orElse : null이던 말던 항상 호출
     * orElseGet : null일 때만 호출
     * @param attributes
     * @return Member 객체
     */
    private Member saveOrUpdate(OAuthAttributes attributes) {
        Member member = memberRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.change(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return memberRepository.save(member);
    }
}
