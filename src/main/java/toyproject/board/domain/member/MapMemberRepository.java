package toyproject.board.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
public class MapMemberRepository implements MemberRepository {

    private static final Map<Long, Member> store = new ConcurrentHashMap<>();

    private static AtomicLong sequence = new AtomicLong(0);

    @Override
    public Member save(Member member) {
        LocalDateTime now = LocalDateTime.now();
        Long memberId = sequence.getAndIncrement();
        Member newMember = member.builder()
                .id(memberId)
                .email(member.getEmail())
                .password(member.getPassword())
                .writer(member.getWriter())
                .createdDate(now)
                .modifiedDate(now)
                .build();
        store.put(memberId, newMember);
        return newMember;
    }

    @Override
    public Member findById(Long id) {
        return store.get(id);
    }

    @Override
    public List<Member> findAll() {
        return new LinkedList<>(store.values());
    }

    @Override
    public void updateMember(Long id, Member upadteMember) {
        Member findMember = findById(id);
        LocalDateTime now = LocalDateTime.now();
        findMember.updateWriter(upadteMember.getWriter());
        findMember.updatePassword(upadteMember.getPassword());
        findMember.updateEmail(upadteMember.getEmail());
        findMember.updateModifiedDate(now);

    }

    @Override
    public void clearStore() {
        store.clear();
    }

    @Override
    public void deleteMember(Long memberId) {
        store.remove(memberId);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return Optional.ofNullable(store.values().stream()
                .filter(member -> member.getEmail().equals(email))
                .findFirst()
                .orElse(null));
    }
}
