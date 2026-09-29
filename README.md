# backend-engineering-lab

[![CI](https://github.com/LIMJAELIN/backend-engineering-lab/actions/workflows/ci.yml/badge.svg)](https://github.com/LIMJAELIN/backend-engineering-lab/actions/workflows/ci.yml)

Hands-on backend engineering lab for Java, Spring, JPA, transactions, and concurrency.

실무 Java/Spring/MyBatis 경험과 별도로, modern Java/Spring 백엔드의 핵심 동작을 **직접 재현하고 실행 가능한 테스트로 검증하기 위한 engineering lab**입니다.

Persistence, transaction, concurrency behavior를 작은 실험으로 분리하고, 단순히 동작시키는 데서 끝내지 않고 원인·적용 조건·trade-off까지 설명할 수 있는 상태를 목표로 합니다.

## Current verified experiments

| Area | Experiment | Verification |
| --- | --- | --- |
| Persistence | Dirty Checking | managed / detached entity의 최종 DB 상태 assertion |
| Persistence | N+1 & Fetch Join | Hibernate statement count `4 → 1` assertion |
| Concurrency | Optimistic Locking | stale transaction commit 실패 + 최종 수량/version assertion |
| Transaction | REQUIRED / REQUIRES_NEW | outer rollback 이후 inner commit 보존 + exception propagation assertion |

현재 CI는 PostgreSQL 18 환경에서 7개 테스트를 실행하며, `main` 최신 검증은 통과 상태를 유지합니다.

각 실험은 `Problem → Reproduction → Observation → Decision → Verification → Trade-off` 구조로 문서화합니다.

## Tech Stack

- Java 21
- Spring Boot 4.0.8
- Spring Data JPA / Hibernate 7.2
- PostgreSQL 18
- JUnit 5 / AssertJ
- GitHub Actions

## Run locally

Docker와 Maven이 설치되어 있다는 전제에서:

```bash
docker compose up -d
mvn test
```

기본 local DB 설정은 공개용 lab credential(`jpalab` / `jpalab`)을 사용하며, 실제 credential은 환경변수로 덮어쓸 수 있습니다.

```text
DB_URL
DB_USERNAME
DB_PASSWORD
```

## Experiments

- [Dirty Checking vs Detached Entity](docs/experiments/dirty-checking.md)
- [N+1 and Fetch Join](docs/experiments/n-plus-one.md)
- [Optimistic Locking with @Version](docs/experiments/optimistic-locking.md)
- [Transaction Propagation: REQUIRED and REQUIRES_NEW](docs/experiments/transaction-propagation.md)

추가 후보와 우선순위는 [Roadmap](docs/ROADMAP.md)에 기록합니다.

## Repository principles

- 실행 가능한 assertion으로 framework 동작을 검증합니다.
- 로그를 한 번 본 것만으로 일반화하지 않습니다.
- framework behavior와 application-level decision을 구분합니다.
- trade-off와 적용 한계를 함께 기록합니다.
- production experience와 lab experience를 구분해서 표현합니다.
- 이해하고 설명할 수 없는 코드는 portfolio evidence로 사용하지 않습니다.

## Scope boundary

JPA/Hibernate는 이 저장소에서 직접 구현·검증한 **hands-on lab experience**이며, production 운영 경험과는 구분합니다.
