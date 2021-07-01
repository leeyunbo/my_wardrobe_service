package com.cloth.wardrobe.domain.community;


import com.cloth.wardrobe.domain.BaseTimeEntity;
import com.cloth.wardrobe.domain.clothes.MethodType;
import com.cloth.wardrobe.domain.member.Member;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;


@Setter
@Getter
@MappedSuperclass
public abstract class Post extends BaseTimeEntity {
    // 멤버
    @OneToOne
    @JoinColumn(name = "member_id")
    Member member;

    public abstract Post changeLikeCnt(Like like, MethodType type);


}