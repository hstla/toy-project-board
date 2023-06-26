package toyproject.board.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
class MapMemberRepositoryTest {

    MapMemberRepository mapMemberRepository = new MapMemberRepository();
    static Member member1;
    static Member member2;

    @AfterEach
    void clearStore() {
        mapMemberRepository.clearStore();
    }

    @BeforeEach
    void firstSave() {
        member1 = Member.builder()
                .email("test1@gmail.com")
                .password("1234")
                .writer("writer1")
                .build();

        member2 = Member.builder()
                .email("test2@gmail.com")
                .password("1234")
                .writer("writer2")
                .build();
    }


    @Test
    void save() {
        //when
        Member saveMember1 = mapMemberRepository.save(member1);
        Member saveMember2 = mapMemberRepository.save(member2);

        //then
        assertThat(saveMember1.getId()).isEqualTo(0L);
        assertThat(saveMember1.getWriter()).isEqualTo("writer1");
        assertThat(saveMember2.getId()).isEqualTo(1L);
        assertThat(saveMember2.getWriter()).isEqualTo("writer2");
    }

    @Test
    void findById() {
        //given
        Member saveMember = mapMemberRepository.save(member1);
        //when
        Member findMember = mapMemberRepository.findById(saveMember.getId());
        //then
        assertThat(saveMember).isEqualTo(findMember);
        log.info("saveMemberId={}", saveMember.getId());
        log.info("findMemberId={}", findMember.getId());
    }

    @Test
    void findAll() {
        //given
        mapMemberRepository.save(member1);
        mapMemberRepository.save(member2);
        //when
        List<Member> allMembers = mapMemberRepository.findAll();
        //then
        assertThat(allMembers.size()).isEqualTo(2);
    }

    @Test
    void updateMember() {
        //given
        Member saveMember = mapMemberRepository.save(member1);
        //when
        Member updateMember = Member.builder()
                .writer("updateWriter")
                .password("updatePass")
                .build();

        mapMemberRepository.updateMember(saveMember.getId(), updateMember);
        Member findMember = mapMemberRepository.findById(saveMember.getId());
        //then
        assertThat(findMember.getWriter()).isEqualTo("updateWriter");
        assertThat(findMember.getPassword()).isEqualTo("updatePass");
        log.info("createDate={}", findMember.getCreatedDate());
        log.info("ModifiedDate={}", findMember.getModifiedDate());
    }

    @Test
    void deleteMember() {
        //given
        Member saveMember1 = mapMemberRepository.save(member1);
        mapMemberRepository.save(member2);
        //when
        mapMemberRepository.deleteMember(saveMember1.getId());
        List<Member> findList = mapMemberRepository.findAll();
        //then
        assertThat(findList.size()).isEqualTo(1);
    }

    @Test
    void clearStore1() {
        //given
        mapMemberRepository.save(member1);
        //when
        mapMemberRepository.clearStore();
        List<Member> allMembers = mapMemberRepository.findAll();
        //then
        assertThat(allMembers.size()).isEqualTo(0);
    }

    @Test
    void findByEmail() {
        //given
        mapMemberRepository.save(member1);
        mapMemberRepository.save(member2);
        //when
        Member findEmailMember = mapMemberRepository.findByEmail("test1@gmail.com").orElse(null);
        //then
        assertThat(findEmailMember.getEmail()).isEqualTo("test1@gmail.com");
        log.info("findEmailMember.getEmail={}", findEmailMember.getId());
    }
}