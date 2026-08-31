# Optimistic Locking with @Version

## Problem
두 transaction이 같은 상품 수량을 읽고 각각 수정하면, 뒤늦게 커밋한 transaction이 먼저 반영된 값을 덮어쓸 수 있다.

## Reproduction
1. 수량 10인 `Product`를 생성한다.
2. 두 개의 독립 `EntityManager` / transaction A, B가 같은 row를 읽는다.
3. A가 수량을 3 감소한 뒤 먼저 commit 한다.
4. B가 이전 version을 기준으로 수량을 4 감소한 뒤 commit을 시도한다.

## Observation
`@Version` 값이 이미 변경되었기 때문에 B commit이 실패하고, 최종 수량은 A가 반영한 7로 유지된다.

## Decision
충돌 빈도가 낮고 재시도나 사용자 재처리가 가능한 상황에서는 처음부터 row를 잠그는 대신 optimistic locking을 사용할 수 있다.

## Verification
`OptimisticLockingTest`가 오래된 version을 가진 transaction의 commit 실패와 최종 수량/version을 assertion한다.

## Trade-off / Limitation
충돌이 매우 빈번하면 반복적인 optimistic lock failure와 retry 비용이 커질 수 있다. 그런 workload에서는 pessimistic locking이나 atomic update 등 다른 선택을 비교해야 한다.
