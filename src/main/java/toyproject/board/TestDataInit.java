package toyproject.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import toyproject.board.domain.member.MapMemberRepository;
import toyproject.board.domain.member.Member;
import toyproject.board.domain.member.MemberRepository;
import toyproject.board.domain.post.MapPostRepository;
import toyproject.board.domain.post.Post;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {
    private final MapPostRepository mapPostRepository;
    private final MapMemberRepository mapMemberRepository;

    @PostConstruct
    public void init() {
        mapPostRepository.save(new Post("writer1", "title1","content1"));
        mapPostRepository.save(new Post("writer2", "title2","content2"));
        mapPostRepository.save(new Post("writer3", "title3","content3"));
        Member member = Member.builder()
                .email("test@gmail.com")
                .password("1234")
                .writer("testWriter")
                .build();
        mapMemberRepository.save(member);
    }
}
