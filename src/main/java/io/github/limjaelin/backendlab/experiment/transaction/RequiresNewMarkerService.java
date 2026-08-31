package io.github.limjaelin.backendlab.experiment.transaction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequiresNewMarkerService {

    private final TransactionMarkerRepository repository;

    public RequiresNewMarkerService(TransactionMarkerRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(String label) {
        repository.save(new TransactionMarker(label));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveThenFail(String label) {
        repository.save(new TransactionMarker(label));
        throw new IllegalStateException("inner transaction failure");
    }
}
