package io.github.limjaelin.backendlab.experiment.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionMarkerRepository extends JpaRepository<TransactionMarker, Long> {
}
