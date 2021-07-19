package com.cloth.wardrobe.domain.community;


import com.cloth.wardrobe.domain.BaseTimeEntity;
import com.cloth.wardrobe.domain.clothes.MethodType;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.MappedSuperclass;


@Setter
@Getter
@MappedSuperclass
public abstract class Post extends BaseTimeEntity {

    public abstract Post changeLikeCnt(Like like, MethodType type);


}