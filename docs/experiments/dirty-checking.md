# Dirty Checking vs Detached Entity

## Problem
JPA의 변경 감지는 어떤 객체에 적용되고, `save()`를 다시 호출하지 않아도 UPDATE가 발생하는 이유는 무엇인가?

## Reproduction
1. `Member`를 영속화하고 flush/clear 한다.
2. 다시 조회한 managed entity의 이름을 변경하고 flush 한다.
3. 별도 테스트에서는 `clear()`로 detached 상태가 된 기존 객체만 변경하고 flush 한다.

## Observation
- managed entity의 변경은 명시적 `save()` 없이 flush 시 DB에 반영된다.
- detached entity의 필드만 바꾼 경우 자동으로 반영되지 않는다.

## Decision
변경 감지를 단순히 "JPA가 setter를 감시한다"고 이해하지 않고, persistence context가 관리하는 entity snapshot과 flush 시점의 차이를 비교하는 동작으로 이해한다.

## Verification
`DirtyCheckingTest`가 managed / detached 두 조건의 최종 DB 값을 각각 assertion으로 검증한다.

## Trade-off / Limitation
flush는 SQL을 DB에 전달하는 시점이지 transaction commit과 같은 의미가 아니다. rollback 가능한 transaction 안에서는 flush 이후에도 최종 커밋이 보장되지 않는다.
