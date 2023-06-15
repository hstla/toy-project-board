package toyproject.board.domain.post;


import java.util.List;

public interface PostRepository {
    Post save(Post post);
    Post findById(Long id);
    List<Post> findAll();
    void update(Long id, Post updateParam);
    void clearStore();
    void deletePost(Long id);
    void updateViewCount(Post post);

}
