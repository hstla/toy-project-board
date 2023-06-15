package toyproject.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import toyproject.board.domain.post.MapPostRepository;
import toyproject.board.domain.post.Post;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {
    private final MapPostRepository mapPostRepository;

    @PostConstruct
    public void init() {
        mapPostRepository.save(new Post("writer1", "title1","content1"));
        mapPostRepository.save(new Post("writer2", "title2","content2"));
        mapPostRepository.save(new Post("writer3", "title3","content3"));
    }
}
