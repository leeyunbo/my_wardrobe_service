package com.cloth.wardrobe.repository;

import com.cloth.wardrobe.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    /**
     * Spring Boot가 만들어 놓은 JPA의 엔티티 매니져
     */
    private final EntityManager em;

    /**
     * 멤버 생성
     * @param member
     */
    public void save(Member member) {
        em.persist(member);
    }

    /**
     * 멤버 조회
     * @param id
     * @return 멤버 객체
     */
    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    /**
     * 멤버 전체 조회
     * @return 멤버 리스트
     */
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    /**
     * 멤버를 이름 기준으로 조회한다.
     * @param name
     * @return 멤버 객체
     */
    public Member findByName(String name) {
        try {
            return em.createQuery("select m from Member m where m.name = :name", Member.class)
                    .setParameter("name", name)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }
}
