package com.cloth.wardrobe.domain.member;

import com.cloth.wardrobe.domain.clothes.Wardrobe;
import com.cloth.wardrobe.domain.community.Comment;
import com.sun.istack.NotNull;
import lombok.Builder;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String account;

    @Column
    private String picture;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberAuthority memberAuthority;

    @Embedded
    private Address address;

    // 옷장
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wardrobe_id")
    private Wardrobe wardrobe;

    // 댓글
    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Member(String name, String account, String picture, MemberAuthority memberAuthority, Address address) {
        this.name = name;
        this.account = account;
        this.picture = picture;
        this.memberAuthority = memberAuthority;
        this.address = address;
    }

    /**
     * 사용자 정보 변경
     * @param name
     * @param city
     * @param street
     * @param zipcode
     */
    public Member change(String name, String picture, String city, String street, String zipcode) {
        this.name = name;
        this.picture = picture;
        this.address = new Address(city, street, zipcode);
        return this;
    }

}
