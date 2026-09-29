package io.github.limjaelin.backendlab.experiment.transaction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaction_markers")
public class TransactionMarker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String label;

    protected TransactionMarker() {
    }

    public TransactionMarker(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
