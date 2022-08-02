package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 생성
            Member member1 = new Member();
            member1.setId(1L);
            member1.setName("member1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setId(2L);
            member2.setName("member2");
            em.persist(member2);

            Member member3 = new Member();
            member3.setId(3L);
            member3.setName("member3");
            em.persist(member3);

            // 조회
            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("fineMember.name = " + findMember.getName());

            // 수정
            findMember.setName("member1.1");
            // em.persist(member); 수정 시에는 필요 없음

            // 삭제
            em.remove(member1);

            // JPQL
            // DB에 저장된 테이블이 아니라 엔티티 객체를 대상으로 조회하며, DB 방언에 따라 SQL 자동 생성
            // 즉 SQL을 추상화하여 특정 DB SQL에 의존하지 않는 객체 지향 SQL
            List<Member> members = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(5)
                    .getResultList();
            for (Member member : members) {
                System.out.println("member.name = " + member.getName());
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }


        emf.close();
    }
}
