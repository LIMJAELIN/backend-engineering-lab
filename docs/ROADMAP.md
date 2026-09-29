# Roadmap

이 저장소는 예제를 많이 모으는 것이 아니라, 면접과 실무 판단에 가치가 높은 backend behavior를 작은 실험으로 검증하는 것을 목표로 합니다.

## Phase 1 — Persistence Foundation

### 1. Dirty Checking & Flush
- managed entity 변경 감지
- flush와 commit의 차이
- `clear()` / `detach()` 이후 동작
- 테스트로 SQL 반영 시점 확인

### 2. N+1 & Fetch Join
- LAZY 연관관계에서 N+1 재현
- fetch join으로 해결
- EAGER 전환을 기본 해결책으로 사용하지 않는 이유
- 조회 목적에 따른 trade-off 정리

## Phase 2 — Concurrency

### 3. Optimistic Locking
- `@Version` 적용
- stale update 충돌 재현
- lost update 문제와 적용 조건 설명
- retry를 application requirement와 분리해서 판단

### 4. Pessimistic Locking
- PostgreSQL row lock 관찰
- lock wait와 deadlock의 차이
- 긴 transaction의 비용 확인

## Phase 3 — Transaction Boundary

### 5. Propagation
- `REQUIRED`
- `REQUIRES_NEW`
- self-invocation / proxy boundary
- 내부 transaction rollback과 Java exception propagation 구분

### 6. Rollback Semantics
- runtime exception
- checked exception
- `rollbackFor`
- 외부 side effect와 DB rollback의 경계

## Phase 4 — Data Integrity

### 7. Unique Constraint Race
- application-level `exists → insert` race 재현
- DB UNIQUE constraint를 최종 방어선으로 사용
- idempotency와 unique constraint의 역할 차이

### 8. Isolation
- READ COMMITTED에서 Non-Repeatable Read 재현
- isolation level을 올릴 때 consistency / concurrency trade-off 관찰

## Completion Rule

한 주제는 다음을 모두 만족할 때 완료로 표시합니다.

- [ ] 실행 가능한 재현 코드가 있다.
- [ ] 자동화된 테스트가 있다.
- [ ] README/문서에서 원인을 설명한다.
- [ ] 선택한 해결책과 대안을 비교한다.
- [ ] trade-off / limitation을 적는다.
- [ ] 코드를 보지 않고 설명할 수 있다.
