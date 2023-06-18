package toyproject.board.domain.member;

import java.util.List;

public interface MemberRepository {
    Member save(Member member);
    Member findById(Long id);
    List<Member> findAll();
    void updateMember(Long id, Member upadteMember);
    void clearStore();
    void deleteMember(Member member);
}
