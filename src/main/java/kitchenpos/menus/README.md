# 메뉴

## 모델링
#### 속성
- `Menu`는 식별자, `Displayed Name`, `Price`, `Menu Products`를 갖는다.
- `Displayed Name`은 이름을 갖는다.
    - 이름은 `Slang`를 포함할 수 없다.
- `Price`는 가격을 갖는다.
    - 가격은 0원 이상이어야 한다.
- Quantity는 수량을 갖는다.
    - 수량은 0개 이상이어야 한다.
- `Menu Products`는 식별자, `Product` 식별자, Quantity를 갖는다.
    - 갯수는 0개 이상이여야 한다.
- `Menu`는 특정 `Menu Group`에 속해야 한다.
- `Menu Group`은 이름을 갖는다.
    - 이름은 존재해야 한다.
#### 기능
- `Register Menu`를 할 수 있다.
    - `Register Menu` 시, `Displayed Name`, `Price`, `Menu Products`는 필수여야 한다.
    - `Register Menu` 시, 1개 이상의 `Product` 로 구성되어야 한다.
    - `Register Menu` 시, Menu Price Policy를 따른다.
- `Change the price`를 할 수 있다.
    - `Change the price` 시, 변경될 `Price`는 0원 이상이여야 한다.
    - `Change the price` 시, Menu Price Policy를 따른다.
- `Menu`를 `Display Menu` 또는 `Hide Menu`를 할 수 있다.
- `View All Menus`를 할 수 있다.

#### 정책
- 메뉴가격정책(Menu Price Policy)
    - `Menu`의 `Price`는 `Menu Products`의 `Price` 총합보다 작거나 같아야 한다
- 비속어정책(Slang Policy)
    - `https://www.purgomalum.com/service/containsprofanity`에서 단어를 필터링 한다


## 용어사전
| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 메뉴 | Menu | 손님들이 보고 주문하게 될 상품의 묶음을 말한다 |
| 메뉴명 | Displayed Name | 메뉴명을 말한다 |
| 비속어 | Slang | 비속어 정책에 정의된 사이트에서 필터링 된 단어 |
| 가격 | Price | 메뉴 가격을 말한다 |
| 메뉴 그룹 | Menu Group | 메뉴를 분류하기 위한 그룹을 말한다 |
| 메뉴에 속한 상품 | Menu Products | 메뉴에 속한 상품을 말한다 |
| 메뉴 노출 | Display Menu | 메뉴를 보이는 상태를 말한다 |
| 메뉴 숨김 | Hide Menu | 메뉴를 보이지 않는 상태를 말한다 |
| 메뉴 등록 | Register Menu | (새로운)메뉴를 등록하는 것을 말한다 |
| 메뉴 가격 변경 | Change the Price | 메규 가격을 변경하는 것을 말한다 |
| 메뉴 목록 조회 | View All Menus | 전체 메뉴를 조회하는 것을 말한다 |
