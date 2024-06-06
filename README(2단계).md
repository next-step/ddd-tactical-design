## 요구 사항

- 키친포스의 요구 사항과 용어 사전, 모델링을 기반으로 메뉴 CONTEXT를 리팩터링한다.
- 메뉴 CONTEXT의 도메인 계층만 먼저 구현한다.
    - `menus` 패키지 밑에 `tobe.domain` 패키지를 만들고 거기서부터 구현을 시작한다.
- 용어 사전과 모델링이 부자연스럽거나 불완전하거나 잘못된 경우 지속적으로 수정한다.
- 새로운 모델에 맞게끔 클래스, 메서드, 모듈의 이름을 다시 지으면서 코드를 리팩터링한다.
- REPOSITORY 구현 시 자신에게 익숙하고 편한 것을 선택하여 진행한다.
- Spring Data JPA
    - [Getting started with Spring Data JPA](https://spring.io/blog/2011/02/10/getting-started-with-spring-data-jpa)
    - [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa)
- Spring JDBC
    - [Accessing Relational Data using JDBC with Spring](https://spring.io/guides/gs/relational-data-access)
- 데이터베이스 스키마 변경 및 마이그레이션이 필요하다면 아래 문서를 적극 활용한다.
    - [DB도 형상관리를 해보자!](https://meetup.toast.com/posts/173)


참고내용 
- h3 : bounded context
- h5 : 하위 도메인 (BC와 동일한 경우 생략)

### 메뉴

- `Menu(메뉴)` 는 식별자, `MenuName(메뉴 이름)`, `MenuPrice(메뉴 가격)`, `MenuGroup(메뉴 그룹)`, `MenuDisplayStatus(메뉴 노출 상태)`, 여러
  개의 `MenuProduct(메뉴 상품)` 를 가진다.
- `MenuName(메뉴 이름)` 은 `Slang(비속어)`을 포함할 수 없다.
- `Menu(메뉴)` 에서 여러 개의 `MenuProduct(메뉴 상품)`를 생성한다.
- `Menu(메뉴)` 에서 `MenuProduct(메뉴 상품)` 의 총 `Price(가격)`을 계산한다.
- `Menu(메뉴)` 에서 `MenuDisplayStatus(메뉴 노출 상태)` 를 `DisplayedMenu(노출된 메뉴)` 로 변경 할 수 있다.
- `MenuPrice(메뉴 가격)` 가 `MenuProduct(메뉴 상품)` 의 총 `Price(가격)` 를 초과하는 경우 `DisplayedMenu(노출된 메뉴)` 로 변경할 수 없다.
- `Menu(메뉴)` 에서 `MenuDisplayStatus(메뉴 노출 상태)` 를 `UndisplayedMenu(숨겨진 메뉴)` 로 변경 할 수 있다.
- `Menu(메뉴)` 에서 `MenuPrice(메뉴 가격)` 를 변경한다.
- `MenuPrice(메뉴 가격)` 륿 변경할 때 `MenuProduct(메뉴 상품)` 의 총 `Price(가격)` 를 초과하는 경우 변경할 수 없다.
  - [X] 0원보다 적은 금액을 입력하는 경우 메뉴 가격을 변경할할 수 없다.

##### 메뉴 상품

- `MenuProduct(메뉴 상품)` 는 `Product(상품)`, `Quantity(수량)` 을 가진다.
- `MenuProduct(메뉴 상품)` 에서 `Product(상품)` 의 총 `Price(가격)` 을 계산한다.
- [ ] 메뉴상품 금액 변경으로 인해 해당 상품이 포함된 메뉴의 가격이 메뉴 구성 전체 상품의 총 금액보다 비싸지는 경우 메뉴가 숨겨진다.

##### 메뉴 그룹

- `MenuGroup(메뉴 그룹)` 은 식별자, `MenuGroupName(메뉴 그룹 이름)` 를 항상 가진다.


### 피드백 TODO
- [ ] boolean 값을 받기 보단 비즈니스 로직을 메서드 내부에서 처리 (Exception)
- [x] Menu 에서 가격검증 -> 삭제
- [ ] 메뉴의 노출 / 비노출 정책은 Product의 가격 변경이 일어나는 경우 필요한 요구사항입니다. 도메인 서비스를 구현하는 주체가 어디가 될지 고민해보세요 ! 
  - [link](https://github.com/next-step/ddd-tactical-design/pull/295#discussion_r1623946968)
- [ ] menuService -> getter대신 메시지보내는 구조로 변경
- [ ] 도메인 서비스는 어떻게 호출될수 있을까요 ?

