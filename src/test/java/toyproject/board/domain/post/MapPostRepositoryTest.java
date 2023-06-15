package toyproject.board.domain.post;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@Slf4j
class MapPostRepositoryTest {
    MapPostRepository mapPostRepository = new MapPostRepository();

    @AfterEach
    void clearStore() {
        mapPostRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Post post = new Post("writer", "hello", "java");
        //when
        Post savePost = mapPostRepository.save(post);
        //then
        Post findPost = mapPostRepository.findById(post.getId());
        log.info("dataTime={}", savePost.getCreatedDate());
        log.info("modifiedDataTime={}", savePost.getModifiedDate());

        assertThat(findPost).isEqualTo(savePost);

    }

    @Test
    void findById() {
        //given
        Post post = new Post("writer", "hello", "java");
        mapPostRepository.save(post);
        //when
        Post findPost = mapPostRepository.findById(1L);
        //then
        assertThat(post).isEqualTo(findPost);
        assertThat(post.getCreatedDate()).isEqualTo(findPost.getModifiedDate());
    }

    @Test
    void findAll() {
        //given
        Post post1 = new Post("writer1", "hello1", "java1");
        Post post2 = new Post("writer2", "hello2", "java2");
        //when
        mapPostRepository.save(post1);
        mapPostRepository.save(post2);
        //then
        assertThat(mapPostRepository.findAll().size()).isEqualTo(2);
    }

    @Test
    void update() {
        //given
        Post post = new Post("writer", "hello", "java");
        mapPostRepository.save(post);
        //when
        post.setTitle("updateHello");
        mapPostRepository.update(1L,post);
        Post updatePost = mapPostRepository.findById(1L);
        //then
        assertThat(updatePost.getTitle()).isEqualTo("updateHello");
        log.info("createDate={}",updatePost.getCreatedDate());
        log.info("modifiedDate={}",updatePost.getModifiedDate());
    }

    @Test
    void deletePost() {
        //given
        Post post1 = new Post("writer1", "hello1", "java1");
        Post post2 = new Post("writer2", "hello2", "java2");
        //when
        mapPostRepository.save(post1);
        mapPostRepository.save(post2);
        mapPostRepository.deletePost(1L);
        //then
        assertThat(mapPostRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    void viewCount() {
        Post post1 = new Post("writer1", "hello1", "java1");
        mapPostRepository.save(post1);
        log.info("viewCount={}",post1.getViewCount());
        mapPostRepository.updateViewCount(post1);
        log.info("viewCount1={}", post1.getViewCount());
    }
}