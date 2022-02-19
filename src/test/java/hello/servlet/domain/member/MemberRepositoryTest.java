package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {
    // 저장소를 불러온다. 싱글톤이므로 get으로 찾아온다.
    MemberRepository memberRepository = MemberRepository.getInstance();

    @AfterEach
    void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void save() {
        Member member = new Member("kim", 20);
        Member saveMember = memberRepository.save(member);
        Long id = saveMember.getId();
        Member findMember = memberRepository.findById(id);
        Assertions.assertThat(findMember).isEqualTo(member);
    }

    @Test
    void findAll() {
        // given
        Member member1 = new Member("kim", 20);
        Member member2 = new Member("kim", 20);
        // when
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findAll();

        // then
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result).contains(member1, member2);


    }
}