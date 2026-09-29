package io.github.limjaelin.backendlab.persistence;

import io.github.limjaelin.backendlab.domain.member.Member;
import io.github.limjaelin.backendlab.domain.order.PurchaseOrder;
import io.github.limjaelin.backendlab.domain.order.PurchaseOrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class NPlusOneTest {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Autowired
    PurchaseOrderRepository orderRepository;

    @Test
    void lazyAssociationProducesOnePlusNQueries() {
        seedThreeOrders();
        Statistics statistics = resetStatistics();

        List<PurchaseOrder> orders = orderRepository.findAll();
        orders.forEach(order -> order.getMember().getName());

        assertThat(statistics.getPrepareStatementCount()).isEqualTo(4);
    }

    @Test
    void fetchJoinLoadsOrdersAndMembersInOneQuery() {
        seedThreeOrders();
        Statistics statistics = resetStatistics();

        List<PurchaseOrder> orders = orderRepository.findAllWithMember();
        orders.forEach(order -> order.getMember().getName());

        assertThat(statistics.getPrepareStatementCount()).isEqualTo(1);
    }

    private void seedThreeOrders() {
        for (String name : List.of("alpha", "beta", "gamma")) {
            Member member = new Member(name);
            entityManager.persist(member);
            entityManager.persist(member.placeOrder());
        }
        entityManager.flush();
        entityManager.clear();
    }

    private Statistics resetStatistics() {
        Statistics statistics = entityManagerFactory
                .unwrap(SessionFactory.class)
                .getStatistics();
        statistics.clear();
        return statistics;
    }
}
