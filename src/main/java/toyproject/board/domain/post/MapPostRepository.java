package toyproject.board.domain.post;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class MapPostRepository implements PostRepository {
    private static final Map<Long, Post> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Post save(Post post) {
        post.setId(++sequence);
        LocalDateTime now = LocalDateTime.now();
        post.setCreatedDate(now);
        post.setModifiedDate(now);
        post.setViewCount(0L);
        store.put(post.getId(), post);
        return post;
    }

    @Override
    public Post findById(Long id) {
        return store.get(id);
    }

    @Override
    public List<Post> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void update(Long id, Post updateParam) {
        Post findPost = findById(id);
        findPost.setTitle(updateParam.getTitle());
        findPost.setContent(updateParam.getContent());
        findPost.setWriter(updateParam.getWriter());
        findPost.setModifiedDate(LocalDateTime.now());
    }

    @Override
    public void clearStore() {
        store.clear();
    }

    @Override
    public void deletePost(Long id) {
        store.remove(id);
    }

    @Override
    public void updateViewCount(Post post) {
        post.setViewCount(post.getViewCount() + 1);
        update(post.getId(), post);
    }
}
