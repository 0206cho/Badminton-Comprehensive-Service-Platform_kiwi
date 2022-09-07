package com.shop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import com.kiwi.constant.ItemSellStatus;
import com.kiwi.constant.Role;
import com.kiwi.shop.dto.MemberFormDto;
import com.kiwi.shop.entity.Item;
import com.kiwi.shop.entity.Member;
import com.kiwi.shop.repository.ItemRepository;
import com.kiwi.shop.service.MemberService;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional  //테스트 클래스에 트랜잭션널 적용시 테스트 실행 후 롤백이 됨
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest(){
        Member member = createMember();
        Member saveMember = memberService.saveMember(member);

        Assertions.assertEquals(member.getEmail(),saveMember.getEmail());
        Assertions.assertEquals(member.getName(),saveMember.getName());
        Assertions.assertEquals(member.getAddress(),saveMember.getAddress());
        Assertions.assertEquals(member.getPassword(),saveMember.getPassword());
        Assertions.assertEquals(member.getRole(),saveMember.getRole());

    }

    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateMemberTest(){
        Member member1 = createMember();
        Member member2 = createMember();

        memberService.saveMember(member1);

        Throwable e = Assertions.assertThrows(IllegalStateException.class, () -> {
            memberService.saveMember(member2);});

        Assertions.assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }

}