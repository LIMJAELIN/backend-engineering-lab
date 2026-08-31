package io.github.limjaelin.backendlab.concurrency;

import io.github.limjaelin.backendlab.domain.product.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.RollbackException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class OptimisticLockingTest {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Test
    void staleTransactionCannotOverwriteAlreadyCommittedVersion() {
        Long productId = createProduct();

        EntityManager entityManagerA = entityManagerFactory.createEntityManager();
        EntityManager entityManagerB = entityManagerFactory.createEntityManager();

        try {
            entityManagerA.getTransaction().begin();
            entityManagerB.getTransaction().begin();

            Product productA = entityManagerA.find(Product.class, productId);
            Product productB = entityManagerB.find(Product.class, productId);

            productA.decreaseStock(3);
            entityManagerA.getTransaction().commit();

            productB.decreaseStock(4);

            assertThatThrownBy(entityManagerB.getTransaction()::commit)
                    .isInstanceOf(RollbackException.class);
        } finally {
            if (entityManagerA.getTransaction().isActive()) {
                entityManagerA.getTransaction().rollback();
            }
            if (entityManagerB.getTransaction().isActive()) {
                entityManagerB.getTransaction().rollback();
            }
            entityManagerA.close();
            entityManagerB.close();
        }

        EntityManager verificationEntityManager = entityManagerFactory.createEntityManager();
        try {
            Product reloaded = verificationEntityManager.find(Product.class, productId);
            assertThat(reloaded.getStock()).isEqualTo(7);
            assertThat(reloaded.getVersion()).isEqualTo(1L);
        } finally {
            verificationEntityManager.close();
        }
    }

    private Long createProduct() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Product product = new Product("keyboard", 10);
            entityManager.persist(product);
            entityManager.getTransaction().commit();
            return product.getId();
        } finally {
            entityManager.close();
        }
    }
}
