package toyproject.board.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
class MapMemberRepositoryTest {

    MapMemberRepository mapMemberRepository = new MapMemberRepository();

    @AfterEach
    void clearStore() {
        mapMemberRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Member member1 = Member.builder()
                .email("ree@gnak.com")
                .password("1234")
                .writer("writer1")
                .build();

        Member member2 = Member.builder()
                .email("ree@gnak1.com")
                .password("1234")
                .writer("writer2")
                .build();

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
        Member member1 = Member.builder()
                .email("ree@gnak.com")
                .password("1234")
                .writer("writer1")
                .build();
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
        Member member1 = Member.builder()
                .email("ree@gnak.com")
                .password("1234")
                .writer("writer1")
                .build();

        Member member2 = Member.builder()
                .email("ree@gnak1.com")
                .password("1234")
                .writer("writer2")
                .build();

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
        Member member = Member.builder()
                .email("ree@gnak.com")
                .password("1234")
                .writer("writer1")
                .build();
        Member saveMember = mapMemberRepository.save(member);
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
        Member member1 = Member.builder()
                .email("ree@gnak.com")
                .password("1234")
                .writer("writer1")
                .build();

        Member member2 = Member.builder()
                .email("ree@gnak1.com")
                .password("1234")
                .writer("writer2")
                .build();

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
        Member member1 = Member.builder()
                .email("ree@gnak.com")
                .password("1234")
                .writer("writer1")
                .build();

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
        Member member1 = Member.builder()
                .email("test@gmail.com")
                .password("1234")
                .writer("writer1")
                .build();

        Member member2 = Member.builder()
                .email("test@gmail1.com")
                .password("1234")
                .writer("writer2")
                .build();

        mapMemberRepository.save(member1);
        mapMemberRepository.save(member2);
        //when
        Member findEmailMember = mapMemberRepository.findByEmail("test@gmail1.com").orElse(null);
        //then
        assertThat(findEmailMember.getEmail()).isEqualTo("test@gmail1.com");
        log.info("findEmailMember.getEmail={}",findEmailMember.getId());
    }
}