# N+1 and Fetch Join

## Problem
`@ManyToOne(fetch = LAZY)` 자체가 N+1을 없애는 것은 아니다. 부모 목록을 조회한 뒤 각 연관 객체에 접근하면 추가 SELECT가 반복될 수 있다.

## Reproduction
서로 다른 `Member`를 참조하는 주문 3건을 만든 뒤:

1. 일반 `findAll()`로 주문 목록을 조회하고 각 `member.name`에 접근한다.
2. `join fetch` query로 같은 데이터를 조회하고 동일하게 접근한다.

## Observation
Hibernate statistics의 prepared statement count를 사용해:

- 일반 LAZY 조회: 주문 목록 1회 + 회원 3회 = 4 statements
- fetch join 조회: 1 statement

으로 검증한다.

## Decision
모든 association을 EAGER로 바꾸지 않고, 해당 use case에서 연관 객체가 반드시 필요한 query만 fetch join을 사용한다.

## Verification
`NPlusOneTest`가 query count를 숫자로 assertion한다. 로그를 눈으로 보고 판단하는 방식에만 의존하지 않는다.

## Trade-off / Limitation
fetch join은 항상 정답이 아니다. 컬렉션 fetch join의 pagination, 중복 row, 조회 폭 증가 같은 trade-off가 있으므로 query 목적에 맞게 선택해야 한다.
