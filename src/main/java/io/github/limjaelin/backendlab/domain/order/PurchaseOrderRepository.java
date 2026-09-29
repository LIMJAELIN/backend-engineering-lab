package io.github.limjaelin.backendlab.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    @Query("select o from PurchaseOrder o join fetch o.member")
    List<PurchaseOrder> findAllWithMember();
}
