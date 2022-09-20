package com.sim.membermanagement.repository;

import com.sim.membermanagement.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository{
    // 구현체 , 메소드 기능구현
    // 초기 개발 단계에서는 구현체로 가벼운 메모리 기반의 데이터 저장소 사용

    private static Map<Long, Member> store = new HashMap<>(); // save() 를 수행할때 저장할 간단한 메모리 , Map<key,value>
    // 실무에서는 동시성 문제가 발생할 수 있어 공유되는 변수에는 ConcurrentHashMap<>() 을 사용

    private static long sequence = 0L; // 시스템이 구분할 id 값의 시퀀스 생성
    // 실무에서는 동시성 문제를 고려해 AtomicLong 사용

    @Override
    public Member save(Member member) {
        member.setId(++sequence); // store 에 save를 수행하기전 member의 id 값에 sequence 값 지정
        store.put(member.getId(), member); // store에 put() 으로 저장
        return member; // result 반환

    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        // store.get(id)로 실행이 가능하지만 결과가 없다면 null 이 반환될 가능성이 있기 때문에 Optional로 감싸서 반환(클라이언트에서 작업가능)
    }

    @Override
    public Optional<Member> findByName(String name) {
        // java8 람다 사용
        // loop
       return store.values().stream()
                .filter(member -> member.getName().equals(name)) // member 에서 getName으로 찾은게 name과 같은지 filtering
                .findAny(); // 아무거나 찾아지면 반환
    }

    @Override
    public List<Member> findAll() {
        // 실무에서 List를 주로 사용 , 루프 돌리기 편함
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
