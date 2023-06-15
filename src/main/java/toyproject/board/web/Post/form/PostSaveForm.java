package toyproject.board.web.Post.form;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class PostSaveForm {
    @NotBlank(message = "작성자를 입력하세요.")
    private String writer;
    @NotBlank(message = "제목을 입력하세요.")
    private String title;
    @NotBlank(message = "내용을 입력하세요.")
    private String content;
}
