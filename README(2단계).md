### 메뉴 그룹

- `MenuGroup(메뉴 그룹)` 은 식별자, `MenuGroupName(메뉴 그룹 이름)` 를 항상 가진다.

### 메뉴

- [ ] `Menu(메뉴)` 는 식별자, `MenuName(메뉴 이름)`, `MenuPrice(메뉴 가격)`, `MenuGroup(메뉴 그룹)`, `MenuDisplayStatus(메뉴 노출 상태)`, 여러
  개의 `MenuProduct(메뉴 상품)` 를 가진다.
- [ ] `MenuName(메뉴 이름)` 은 `Slang(비속어)`을 포함할 수 없다.
- [ ] `Menu(메뉴)` 에서 여러 개의 `MenuProduct(메뉴 상품)`를 생성한다.
- [ ] `Menu(메뉴)` 에서 `MenuProduct(메뉴 상품)` 의 총 `Price(가격)`을 계산한다.
- [ ] `Menu(메뉴)` 에서 `MenuDisplayStatus(메뉴 노출 상태)` 를 `DisplayedMenu(노출된 메뉴)` 로 변경 할 수 있다.
- [ ] `MenuPrice(메뉴 가격)` 가 `MenuProduct(메뉴 상품)` 의 총 `Price(가격)` 를 초과하는 경우 `DisplayedMenu(노출된 메뉴)` 로 변경할 수 없다.
- [ ] `Menu(메뉴)` 에서 `MenuDisplayStatus(메뉴 노출 상태)` 를 `UndisplayedMenu(숨겨진 메뉴)` 로 변경 할 수 있다.
- [ ] `Menu(메뉴)` 에서 `MenuPrice(메뉴 가격)` 를 변경한다.
- [ ] `MenuPrice(메뉴 가격)` 륿 변경할 때 `MenuProduct(메뉴 상품)` 의 총 `Price(가격)` 를 초과하는 경우 변경할 수 없다.
- [x] `MenuProduct(메뉴 상품)` 는 `Product(상품)`, `Quantity(수량)` 을 가진다.
- [X] `MenuProduct(메뉴 상품)` 에서 `Product(상품)` 의 총 `Price(가격)` 을 계산한다.
