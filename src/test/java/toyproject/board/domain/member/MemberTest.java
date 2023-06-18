package toyproject.board.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

@Slf4j
class MemberTest {

    @Test
    void updateModifiedDate () {
        LocalDateTime now = LocalDateTime.now();
        Member member = Member.builder()
                .id(0L)
                .email("efe@gmail.com")
                .createdDate(now)
                .modifiedDate(now)
                .password("1234asdf")
                .writer("hi")
                .build();

        member.updateWriter("hello");
        Assertions.assertThat(member.getWriter()).isEqualTo("hello");
    }
}