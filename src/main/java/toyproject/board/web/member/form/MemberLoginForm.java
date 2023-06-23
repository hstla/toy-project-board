package toyproject.board.web.member.form;

import lombok.Builder;
import lombok.Getter;
import toyproject.board.domain.member.Member;

@Getter
public class MemberLoginForm {
    private String email;
    private String password;

    @Builder
    public MemberLoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Member FormTOMember() {
        return Member.builder()
                .email(this.getEmail())
                .password(this.getPassword())
                .build();
    }
}