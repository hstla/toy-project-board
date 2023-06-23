package toyproject.board.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import toyproject.board.web.member.form.MemberJoinForm;

import java.util.Optional;

@Slf4j
class MemberServiceTest {
    MapMemberRepository mapMemberRepository = new MapMemberRepository();
    MemberService memberService = new MemberService();

    @Test
    void login() {
        //given
        Member member = Member.builder()
                .email("test@gmail.com")
                .password("1234")
                .writer("writer1")
                .build();
        mapMemberRepository.save(member);
        //when
        Member login1 = memberService.login("test@gmail.com", "1234");
        Member login2 = memberService.login("dfdf", "1234");

        //then
        Assertions.assertThat(login1.getEmail()).isEqualTo(member.getEmail());
        Assertions.assertThat(login2).isNull();

    }

    @Test
    void join() {
        //given
        MemberJoinForm joinForm = MemberJoinForm.builder()
                .email("test@gmail.com")
                .password("1234")
                .writer("testWriter")
                .confirmPassword("1234")
                .build();
        //when
        Member joinMember = memberService.join(joinForm.FormTOMember());
        Member findMember = mapMemberRepository.findByEmail(joinMember.getEmail()).orElse(null);

        //then
        Assertions.assertThat(joinMember).isEqualTo(findMember);
    }

    @Test
    void withdrawal() {
        //given
        MemberJoinForm joinForm = MemberJoinForm.builder()
                .email("test@gmail.com")
                .password("1234")
                .writer("testWriter")
                .confirmPassword("1234")
                .build();
        //when
        Member joinMember = memberService.join(joinForm.FormTOMember());
        Optional<Member> optFindMember1 = mapMemberRepository.findByEmail(joinMember.getEmail());

        memberService.withdrawal(optFindMember1.get().getId());
        Optional<Member> optFindMember2 = mapMemberRepository.findByEmail(joinForm.getEmail());

        //then
        if (optFindMember1.isPresent()) {
            Assertions.assertThat(optFindMember1.get().getId()).isEqualTo(0);
        }

        Assertions.assertThat(optFindMember2.orElse(null)).isEqualTo(null);

    }
}