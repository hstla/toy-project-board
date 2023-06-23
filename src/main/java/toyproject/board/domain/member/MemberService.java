package toyproject.board.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MapMemberRepository mapMemberRepository = new MapMemberRepository();

    /**
     * 로그인 성공 시 return member
     * 로그인 실패 시 return null
     */
    public Member login(String loginEmail, String loginPassword) {
        return mapMemberRepository.findByEmail(loginEmail)
                .filter(m -> m.getPassword().equals(loginPassword)).orElse(null);
    }

    public Member join(Member member) {
        Member saveMember = Member.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .writer(member.getWriter())
                .build();
        Member joinMember = mapMemberRepository.save(saveMember);
        return joinMember;
    }

    public void withdrawal(Long memberId) {
        mapMemberRepository.deleteMember(memberId);
    }
}
