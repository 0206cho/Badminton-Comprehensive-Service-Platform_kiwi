package com.kiwi.member.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kiwi.member.constant.Address;
import com.kiwi.member.constant.Bank;
import com.kiwi.member.constant.Gender;
import com.kiwi.member.constant.Role;
import com.kiwi.member.dto.MemberFormDto;
import com.kiwi.shop.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name="member")
@Getter @Setter
@NoArgsConstructor
@ToString
public class Member extends BaseEntity {
    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="member_name")
    private String name;

    // 회원은 이메일을 통해 유일하게 구분해야 하기 때문에, 동일한 값이 DB에 저장할 수 없도록 unique 속성 지정한다.
    @Column(unique = true, name="member_email")
    private String email;
    
    @Column(name="member_password")
    private String password;
    
    
    // 주소
    @Enumerated(EnumType.STRING)
    @Column(name="member_address")
    private Address address;
    
    // 핸드폰 번호
    @Column(name="member_pnum")
    private String pnum;
    
    // 생년월일
    @Column(name="member_birthday")
    private String birthday;
    
    // 성별
    @Enumerated(EnumType.STRING)
    @Column(name="member_gender")
    private Gender gender;
    
    // 계좌은행
    @Enumerated(EnumType.STRING)
    @Column(name="member_bname")
    private Bank bname;
    
    // 계좌번호
    @Column(name="member_bnumber")
    private String bnumber;

    // enum 타입은 기본적으로 순서가 저장되는데 순서가 바뀌면 문제가 생기므로 STRING 옵션 설정한다.
    @Enumerated(EnumType.STRING)
    private Role role;
    
    // OAuth로그인 서비스 제공자
    private String provider;
    
    // OAuth로그인한 내 계정에 대한 Id
    private String providerId;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.ADMIN);
        member.setPnum(memberFormDto.getPnum());
        member.setBirthday(memberFormDto.getBirthday());
        member.setGender(memberFormDto.getGender());
        member.setBname(memberFormDto.getBname());
        member.setBnumber(memberFormDto.getBnumber());
        return member;
    }
    
    @Builder
	public Member(String email, String password, Role role, String provider, String providerId,String name) {
		this.email = email;
		this.password = password;
		this.role = role;
		this.provider = provider;
		this.providerId = providerId;
		this.name = name;
	}
    
}
