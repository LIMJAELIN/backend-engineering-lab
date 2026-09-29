# Roadmap

이 저장소는 예제를 많이 모으는 것보다, 면접과 실무 판단에 직접 연결되는 동작을 작은 실험으로 검증하는 데 집중합니다.

## Current

- [x] Dirty Checking vs detached entity
- [x] LAZY N+1 reproduction and fetch join
- [x] `@Version` optimistic locking
- [x] `REQUIRED` / `REQUIRES_NEW` transaction boundary and exception propagation

## Gate before expansion

현재 4개 experiment를 코드를 보지 않고 다음까지 설명할 수 있어야 합니다.

- 왜 문제가 생기는가
- 어떤 조건에서 재현되는가
- 왜 이 해결책을 선택했는가
- 다른 선택지는 무엇인가
- trade-off / limitation은 무엇인가
- 테스트가 실제로 무엇을 증명하는가

이 delayed interview check가 불안정하면 새 예제를 추가하기보다 기존 주제를 먼저 복습합니다.

## Next candidates — priority order

- [ ] Association ownership: `mappedBy`, owning side, cascade, orphanRemoval
- [ ] Pessimistic locking and lock wait
- [ ] Database UNIQUE constraint vs application-level duplicate check race
- [ ] READ COMMITTED non-repeatable read
- [ ] Rollback rules for checked vs unchecked exceptions
- [ ] `UnexpectedRollbackException`

새 항목은 실제 학습/면접 gap이 확인될 때만 추가합니다. 단순 CRUD나 비슷한 예제 수를 늘리는 작업은 우선순위가 낮습니다.

## Completion Rule

한 주제는 다음을 모두 만족할 때 완료로 표시합니다.

- [x] 실행 가능한 재현 코드가 있다.
- [x] 자동화된 테스트가 있다.
- [x] README/문서에서 원인을 설명한다.
- [x] 선택한 해결책과 대안을 비교한다.
- [x] trade-off / limitation을 적는다.
- [ ] 코드를 보지 않고 설명할 수 있다.

마지막 항목은 저장소가 아니라 실제 면접 복습에서 확인합니다.
