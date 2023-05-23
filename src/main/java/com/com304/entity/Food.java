package com.com304.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Food extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long fno;

    @Column(nullable = false)
    private String cate; // 카테고리

    @Column(nullable = false)
    private String name; // 상호명

    @Column(nullable = false)
    private String managerId; // 관리자 ID

    @Column(nullable = false)
    private String post; // 우편번호
    @Column(nullable = false)
    private String adrs; // 주소
    @Column(nullable = false)
    private String detailAdrs; // 세부주소

    @Column(nullable = false)
    private String intro; // 소개
    @Column(nullable = false)
    private String contact1; // 연락처
    private String contact2; // 연락처
    private String contact3; // 연락처

    @Column(nullable = false)
    private String close; //// 휴무일
    @Column(nullable = false)
    private String timeOpen; // 영업시간(시작)
    // @Column(nullable = false)
    private String timeClose; // 영업시간(종료)

    private String imgName; // 이미지 파일명

    private String oriImgName; // 원본 이미지 파일명

    private String imgUrl; // 이미지 조회 경로


    public void change(String intro, String contact1, String contact2, String contact3, String close, String timeOpen, String timeClose) {
        this.intro = intro;
        this.contact1 = contact1;
        this.contact2 = contact2;
        this.contact3 = contact3;
        this.close = close;
        this.timeOpen = timeOpen;
        this.timeClose = timeClose;
    }

    public void updateImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
