package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberRepository {

    /*
    static은 변수 공간을 미리 컴파일 빌드 단계에서 할당한다.
    따라서 MemberRepository가 new로 계속 생성되더라도, static된것은 기존꺼를 들고온다
    그래서 sequence가 유지될 수 있게 한다.
    싱글톤으로 구현했기 때문에 없어도 되긴 한다. 왜냐면 new가 없기 때문이다.

    여기서도 동시성 문제는 해결되지 않는데, 이 예제에서는 무시한다. */
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    private static final MemberRepository instance = new MemberRepository();

    // 조회만 가능하다
    public static MemberRepository getInstance() {
        return instance; // 처음에 생성된 이것만 나옴
    }

    // 생성자를 막아서 처음한번만 생성되고, 이후 생성이 불가능한 싱글톤으로 만든다.
    private MemberRepository() {
    }

    // 여기부터 기능
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }
    public Member findById(long id) {
        Member member = store.get(id);
        return member;
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
