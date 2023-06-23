package toyproject.board.web.member.form;

import lombok.Builder;
import lombok.Getter;
import toyproject.board.domain.member.Member;

@Getter
public class MemberJoinForm {
    private String writer;
    private String email;
    private String password;
    private String confirmPassword;

    @Builder
    public MemberJoinForm(String writer, String email, String password, String confirmPassword) {
        this.writer = writer;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public Member FormTOMember() {
        return Member.builder()
                .email(this.getEmail())
                .writer(this.getWriter())
                .password(this.getPassword())
                .build();
    }
}
