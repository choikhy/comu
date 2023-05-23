package com.com304.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodDTO {
    private Long fno;

    private String managerId; // 등록자

    private String name; // 상호명
    private String cate; // 카테고리

    private String post; // 우편번호
    private String adrs; // 주소
    private String detailAdrs; // 세부주소

    private String intro; // 소개
    private String contact1; // 연락처
    private String contact2; // 연락처
    private String contact3; // 연락처

    private String close; // 휴무일
    private String timeOpen; // 운영시간
    private String timeClose;

    private String thumbnailUrl;

    private String imgName;
    private String oriImgName;
    private String imgUrl;
}
