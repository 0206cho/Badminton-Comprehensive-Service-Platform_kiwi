package com.kiwi.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.kiwi.member.constant.Address;
import com.kiwi.member.constant.Bank;
import com.kiwi.member.constant.Gender;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OauthAddInfo {
 
    @NotNull(message = "주소는 반드시 선택해주세요.")
    private Address address;
    
    @NotEmpty(message = "휴대폰 번호는 반드시 입력해주세요.")
    private String pnum;
    
    @NotEmpty(message = "생년월일은 반드시 입력해주세요.")
    @Length(min=6, max=6, message = "생년월일 형식을 맞춰주세요. ex) yymmdd")
    private String birthday;
    
    @NotNull(message = "성별은 반드시 선택해주세요.")
    private Gender gender;
    
    @NotNull(message = "계좌은행은 반드시 선택해주세요.")
    private Bank bname;
    
    @NotEmpty(message = "계좌번호는 반드시 입력해주세요.")
    private String bnumber;

}
