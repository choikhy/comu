package com.com304.entity;

import com.com304.constant.Role;
import com.com304.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter@Setter
@ToString
@DynamicUpdate
public class Member extends BaseEntity{
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idNo;
    @Column(unique = true)
    private String id;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String email;
    private String pw;
    private String birth;
    private String gender;
    private String pno_1;
    private String pno_2;
    private String pno_3;
    public String post;
    private String adrs;
    private String detailadrs;//세부주소
    @Enumerated(EnumType.STRING)
    private Role role;
    private String imgName;
    private String imgUrl;

    public static Member createMember(MemberDTO memberDTO, PasswordEncoder passwordEncoder){
        Member member=new Member();
        member.setId(memberDTO.getId());
        member.setName(memberDTO.getName());
        member.setEmail(memberDTO.getEmail());
        member.setPno_1(memberDTO.getPno_1());
        member.setPno_2(memberDTO.getPno_2());
        member.setPno_3(memberDTO.getPno_3());
        member.setGender(memberDTO.getGender());
        member.setBirth(memberDTO.getBirth());
        member.setPost(memberDTO.getPost());
        member.setAdrs(memberDTO.getAdrs());
        member.setDetailadrs(memberDTO.getDetailadrs());
        String password=passwordEncoder.encode(memberDTO.getPw());
        member.setPw(password);
        member.setRole(Role.USER);//USER와 ADMIN으로 스왑가능(회원가입진행)
        return member;
    }

    public void updateItemImg(String oriImgName, String imgName, String imgUrl){
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }

}
