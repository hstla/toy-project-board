package toyproject.board.web.member.form;

import lombok.Builder;
import lombok.Getter;
import toyproject.board.domain.member.Member;

import javax.validation.constraints.NotBlank;

@Getter
public class MemberUpdateForm {
    private Long id;
    private String email;
    @NotBlank
    private String password;
    private String writer;


    @Builder
    public MemberUpdateForm(Long id, String email, String password, String writer) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.writer = writer;
    }

    public Member FormTOMember() {
        return Member.builder()
                .email(this.getEmail())
                .writer(this.getWriter())
                .password(this.getPassword())
                .build();
    }
}