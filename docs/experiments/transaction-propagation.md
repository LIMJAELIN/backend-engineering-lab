# Transaction Propagation: REQUIRED and REQUIRES_NEW

## Problem
`REQUIRES_NEW`를 사용하면 inner transaction은 독립되지만, Java exception까지 자동으로 격리되는 것은 아니다.

## Reproduction
서로 다른 Spring bean을 통해 proxy 경계를 확보한 뒤 두 상황을 검증한다.

### Scenario A
- outer `REQUIRED` transaction에서 marker 저장
- inner `REQUIRES_NEW`가 별도 marker 저장 후 commit
- outer가 이후 예외를 던져 rollback

### Scenario B
- outer에서 marker 저장
- inner `REQUIRES_NEW`가 marker 저장 후 예외 발생
- inner rollback + 예외가 outer로 전파

## Observation
- Scenario A: inner marker만 남는다.
- Scenario B: inner와 outer marker 모두 남지 않는다.

## Decision
`REQUIRES_NEW`는 transaction commit/rollback boundary를 분리하는 수단으로 이해한다. 예외를 outer에 전달할지 catch/translate할지는 별도의 application decision이다.

## Verification
`TransactionPropagationTest`가 두 scenario의 최종 DB 상태와 발생 예외를 assertion한다.

## Trade-off / Limitation
`REQUIRES_NEW`는 별도 connection이 필요할 수 있어 connection pool pressure를 높일 수 있다. 또한 외부 API 같은 non-transactional side effect까지 원자적으로 되돌려 주지 않는다.
