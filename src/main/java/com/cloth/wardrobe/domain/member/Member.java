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
    private boolean loginCheck() {
        boolean check = false;


        return check;
    }

}
