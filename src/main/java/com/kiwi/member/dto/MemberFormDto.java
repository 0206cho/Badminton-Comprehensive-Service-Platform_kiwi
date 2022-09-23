package com.kiwi.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import com.kiwi.constant.Address;
import com.kiwi.constant.Bank;
import com.kiwi.constant.Gender;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class MemberFormDto {

    @NotBlank(message = "이름은 반드시 입력해주세요.")
    private String name;

    @NotEmpty(message = "이메일은 반드시 입력해주세요.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "비밀번호는 반드시 입력해주세요.")
    @Length(min=8, max=16, message = "비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
    private String password;
    
    @NotNull(message = "주소는 반드시 선택해주세요.")
    private Address address;
    
    @NotEmpty(message = "휴대폰 번호는 반드시 입력해주세요.")
    private String pnum;
    
    
    @NotEmpty(message = "생년월일은 반드시 입력해주세요.")
    @Length(min=6, max=6, message = "생년월일 형식을 맞춰주세요. ex) yy-mm-dd")
    private String birthday;
    
    @NotNull(message = "성별은 반드시 선택해주세요.")
    private Gender gender;
    
    @NotNull(message = "계좌은행은 반드시 선택해주세요.")
    private Bank bname;
    
    @NotEmpty(message = "계좌번호는 반드시 입력해주세요.")
    private String bnumber;
}
