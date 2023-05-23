package com.com304.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter@Setter
public class MemberDTO {
    @NotBlank(message = "아이디를 입력해주세요")
    @Pattern(regexp="\\w{6,12}", message = "아이디는 6~12자 사이로 입력해주세요")
    private String id;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String pw;

    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @NotEmpty(message = "생년월일을 입력해주세요.")
    private String birth;

    @NotEmpty(message = "성별을 입력해주세요.")
    private String gender;

    @NotEmpty(message = "휴대폰 번호는 필수 입력 값입니다.")
    @Length(min = 3,max = 3,message = "번호를 짧거나 길게 입력하셨습니다.")
    private String pno_1;
    @NotEmpty(message = "휴대폰 번호는 필수 입력 값입니다.")
    @Length(min = 4,max = 4,message = "번호를 짧거나 길게 입력하셨습니다.")
    private String pno_2;
    @NotEmpty(message = "휴대폰 번호는 필수 입력 값입니다.")
    @Length(min = 4,max = 4,message = "번호를 짧거나 길게 입력하셨습니다.")
    private String pno_3;

    private String post;
    @NotEmpty(message = "주소는 필수 입력 값입니다.")
    private String adrs;
    @NotEmpty(message = "세부 주소를 입력해주세요.")
    private String detailadrs;

    private LocalDateTime regDate;
    private LocalDateTime modDate;


}
