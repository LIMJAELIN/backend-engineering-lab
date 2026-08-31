package io.github.limjaelin.backendlab.transaction;

import io.github.limjaelin.backendlab.experiment.transaction.PropagationScenarioService;
import io.github.limjaelin.backendlab.experiment.transaction.TransactionMarkerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class TransactionPropagationTest {

    @Autowired
    PropagationScenarioService scenarioService;

    @Autowired
    TransactionMarkerRepository repository;

    @BeforeEach
    void clearMarkers() {
        repository.deleteAll();
    }

    @Test
    void requiresNewCommitSurvivesOuterRollback() {
        assertThatThrownBy(scenarioService::outerFailsAfterInnerCommit)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("outer transaction failure");

        assertThat(repository.findAll())
                .extracting(marker -> marker.getLabel())
                .containsExactly("inner");
    }

    @Test
    void requiresNewDoesNotIsolateExceptionPropagation() {
        assertThatThrownBy(scenarioService::innerFailurePropagatesToOuter)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("inner transaction failure");

        assertThat(repository.findAll()).isEmpty();
    }
}
