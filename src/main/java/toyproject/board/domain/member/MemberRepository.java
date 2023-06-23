package toyproject.board.domain.member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Member findById(Long id);
    List<Member> findAll();
    void updateMember(Long id, Member upadteMember);
    void clearStore();
    void deleteMember(Long memberId);

    Optional<Member> findByEmail(String email);
}
