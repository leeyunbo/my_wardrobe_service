package com.cloth.wardrobe.config.auth;

import com.cloth.wardrobe.entity.member.MemberRepository;
import com.cloth.wardrobe.exception.DoNotFoundContentException;
import com.cloth.wardrobe.exception.GoogleIdAuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .map(m -> new CustomUserDetails(m,
                        Collections.singleton(
                                new SimpleGrantedAuthority(m.getRole().getKey())
                        ))
                )
                .orElseThrow(DoNotFoundContentException::new);
    }
}
