package io.github.limjaelin.backendlab.experiment.transaction;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PropagationScenarioService {

    private final TransactionMarkerRepository repository;
    private final RequiresNewMarkerService requiresNewMarkerService;

    public PropagationScenarioService(
            TransactionMarkerRepository repository,
            RequiresNewMarkerService requiresNewMarkerService
    ) {
        this.repository = repository;
        this.requiresNewMarkerService = requiresNewMarkerService;
    }

    @Transactional
    public void outerFailsAfterInnerCommit() {
        repository.save(new TransactionMarker("outer"));
        requiresNewMarkerService.save("inner");
        throw new IllegalStateException("outer transaction failure");
    }

    @Transactional
    public void innerFailurePropagatesToOuter() {
        repository.save(new TransactionMarker("outer"));
        requiresNewMarkerService.saveThenFail("inner");
    }
}
