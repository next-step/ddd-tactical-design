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

### 메뉴 그룹

- [X] `MenuGroup(메뉴 그룹)` 은 식별자, `MenuGroupName(메뉴 그룹 이름)` 를 항상 가진다.

### 메뉴

- [x] `MenuProduct(메뉴 상품)` 는 `Product(상품)`, `Quantity(수량)` 을 가진다.
- [X] `MenuProduct(메뉴 상품)` 에서 `Product(상품)` 의 총 `Price(가격)` 을 계산한다.
- [X] `Menu(메뉴)` 는 식별자, `MenuName(메뉴 이름)`, `MenuPrice(메뉴 가격)`, `MenuGroup(메뉴 그룹)`, `MenuDisplayStatus(메뉴 노출 상태)`, 여러
  개의 `MenuProduct(메뉴 상품)` 를 가진다.
- [ ] `MenuName(메뉴 이름)` 은 `Slang(비속어)`을 포함할 수 없다.
- [ ] `Menu(메뉴)` 에서 여러 개의 `MenuProduct(메뉴 상품)`를 생성한다.
- [ ] `Menu(메뉴)` 에서 `MenuProduct(메뉴 상품)` 의 총 `Price(가격)`을 계산한다.
- [ ] `Menu(메뉴)` 에서 `MenuDisplayStatus(메뉴 노출 상태)` 를 `DisplayedMenu(노출된 메뉴)` 로 변경 할 수 있다.
- [ ] `MenuPrice(메뉴 가격)` 가 `MenuProduct(메뉴 상품)` 의 총 `Price(가격)` 를 초과하는 경우 `DisplayedMenu(노출된 메뉴)` 로 변경할 수 없다.
- [ ] `Menu(메뉴)` 에서 `MenuDisplayStatus(메뉴 노출 상태)` 를 `UndisplayedMenu(숨겨진 메뉴)` 로 변경 할 수 있다.
- [ ] `Menu(메뉴)` 에서 `MenuPrice(메뉴 가격)` 를 변경한다.
- [ ] `MenuPrice(메뉴 가격)` 륿 변경할 때 `MenuProduct(메뉴 상품)` 의 총 `Price(가격)` 를 초과하는 경우 변경할 수 없다.

