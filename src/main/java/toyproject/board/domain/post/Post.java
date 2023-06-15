package toyproject.board.domain.post;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Post {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private Long viewCount;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;


    public Post(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }
}
