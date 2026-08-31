package io.github.limjaelin.backendlab.domain.member;

import io.github.limjaelin.backendlab.domain.order.PurchaseOrder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private final List<PurchaseOrder> orders = new ArrayList<>();

    protected Member() {
    }

    public Member(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public List<PurchaseOrder> getOrders() {
        return List.copyOf(orders);
    }

    public PurchaseOrder placeOrder() {
        PurchaseOrder order = new PurchaseOrder(this);
        orders.add(order);
        return order;
    }
}
