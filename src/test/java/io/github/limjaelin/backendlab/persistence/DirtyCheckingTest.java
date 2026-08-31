package io.github.limjaelin.backendlab.persistence;

import io.github.limjaelin.backendlab.domain.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DirtyCheckingTest {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    void managedEntityChangeIsFlushedWithoutExplicitSave() {
        Member member = new Member("before");
        entityManager.persist(member);
        entityManager.flush();
        entityManager.clear();

        Member managedMember = entityManager.find(Member.class, member.getId());
        managedMember.changeName("after");

        entityManager.flush();
        entityManager.clear();

        Member reloaded = entityManager.find(Member.class, member.getId());
        assertThat(reloaded.getName()).isEqualTo("after");
    }

    @Test
    void detachedEntityChangeIsNotFlushedAutomatically() {
        Member member = new Member("before");
        entityManager.persist(member);
        entityManager.flush();
        entityManager.clear();

        member.changeName("after");
        entityManager.flush();

        Member reloaded = entityManager.find(Member.class, member.getId());
        assertThat(reloaded.getName()).isEqualTo("before");
    }
}
