package com.cloth.wardrobe.domain.member;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private MemberAuthority memberAuthority = MemberAuthority.COMMON;

    @Embedded
    private Address address;
}
