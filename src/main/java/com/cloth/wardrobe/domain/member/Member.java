package com.cloth.wardrobe.domain.member;

import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.domain.community.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
@Getter @Setter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Column(unique=true)
    private String account;

    private String password;

    @Enumerated(EnumType.STRING)
    private MemberAuthority memberAuthority = MemberAuthority.COMMON;

    @Embedded
    private Address address;

    // 옷장
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wardrobe_id")
    private Wardrobe wardrobe;

    // 댓글
    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    // 로그인 체크

    /**
     * 로그인 체크 기능, 잠시 대기
     * @return
     */
    public boolean loginCheck() {
        boolean check = false;


        return check;
    }

    /**
     * 사용자 정보 변경
     * @param name
     * @param password
     * @param city
     * @param street
     * @param zipcode
     * @param memberAuthority
     */
    public void change(String name, String password, String city, String street, String zipcode, MemberAuthority memberAuthority) {
        this.name = name;
        this.password = password;
        this.address = new Address(city, street, zipcode);
        this.memberAuthority = memberAuthority;
    }

}
