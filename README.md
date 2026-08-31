# backend-engineering-lab

Hands-on backend engineering lab for Java, Spring, JPA, transactions, and concurrency.

이 저장소는 production 경력을 가장하기 위한 예제 모음이 아니라, 현대 Java/Spring 백엔드의 핵심 동작을 **직접 재현하고 테스트로 검증하기 위한 engineering lab**입니다.

## Why this repository exists

실무에서는 Java/Spring/MyBatis 기반 B2B ERP를 개발하고 있습니다. 이 저장소에서는 그 경험을 바탕으로 현대 Spring/JPA 환경에서 persistence, transaction, concurrency 동작을 작은 실험으로 분리해 검증합니다.

각 실험은 단순 구현보다 다음 질문에 답하는 것을 목표로 합니다.

1. 어떤 문제가 발생하는가?
2. 왜 발생하는가?
3. 어떻게 재현할 수 있는가?
4. 어떤 해결책을 선택했는가?
5. 다른 선택지와 trade-off는 무엇인가?
6. 테스트는 정확히 무엇을 증명하는가?

## Tech Stack

- Java 21+
- Spring Boot
- Spring Data JPA / Hibernate
- PostgreSQL
- JUnit 5

> 정확한 버전은 실제 프로젝트 코드가 들어온 뒤 build configuration을 source of truth로 사용합니다.

## Initial Experiments

| Area | Experiment | Goal | Status |
| --- | --- | --- | --- |
| Persistence | Dirty Checking & Flush | 변경 감지와 flush/commit 시점 구분 | Planned |
| Persistence | N+1 & Fetch Join | LAZY 연관관계에서 N+1 재현 및 해결 | Planned |
| Concurrency | Optimistic Locking | `@Version` 기반 lost update 방지 | Planned |
| Transaction | Propagation | `REQUIRED` / `REQUIRES_NEW` 경계와 예외 전파 검증 | Planned |

추가 후보는 pessimistic locking, unique-constraint race, isolation level, rollback semantics입니다.

## Experiment Format

각 실험은 가능한 한 아래 구조를 따릅니다.

```text
Problem
→ Reproduction
→ Observation
→ Decision
→ Implementation
→ Verification
→ Trade-off / Limitation
```

문서 템플릿은 [`docs/EXPERIMENT_TEMPLATE.md`](docs/EXPERIMENT_TEMPLATE.md)를 사용합니다.

## Repository Principles

- 결과를 먼저 정하고 코드를 끼워 맞추지 않습니다.
- 실행 가능한 테스트로 동작을 검증합니다.
- framework 동작과 application-level 판단을 구분합니다.
- 한 번의 성공 실행을 일반화하지 않습니다.
- production experience와 lab experience를 구분해서 표현합니다.
- 이해하지 못한 코드는 portfolio evidence로 사용하지 않습니다.

## Scope Boundary

이 저장소의 JPA/Hibernate 코드는 **학습 및 검증용 lab experience**입니다. 실제 production에서 JPA/Hibernate를 운영한 경력으로 표현하지 않습니다.

## Roadmap

세부 작업 순서는 [`docs/ROADMAP.md`](docs/ROADMAP.md)를 참고합니다.
