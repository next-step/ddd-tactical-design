# 상품

## 모델링
#### 속성
- `Product`는 식별자, `Displayed Name` 와 `Price`를 갖는다.
- `Displayed Name`은 이름을 갖는다.
    - 이름은 없거나 빈 값일 수 없다.
    - 이름은 `Slang`를 포함할 수 없다.
- `Price`는 가격을 갖는다.
    - 가격은 0원 이상이어야 한다.
#### 기능
- `Register Product`를 할 수 있다.
    - `Register Product` 시, `Displayed Name`과 `Price`는 필수여야 한다.
    - `Register Product` 시, `Price`는 0원 이상이다.
- `Change the price`를 할 수 있다.
    - `Change the price` 시, 변경될 `Price`는 0원 이상이여야 한다.
    - `Change the price` 시, Menu Display Policy를 따른다.
- `View All Products`가 가능하다.

#### 정책
- 메뉴노출정책(Menu Display Policy)
    - `Product`의 `Change the Price`가 일어났을 때, 메뉴가격정책을 만족하지 못한 메뉴는 숨겨진다
- 메뉴가격정책(Menu Price Policy)
    - `Menu`의 `Price`는 `Menu Products`의 `Price` 총합보다 작거나 같아야 한다
- 비속어정책(Slang Policy)
    - `https://www.purgomalum.com/service/containsprofanity`에서 단어를 필터링 한다


## 용어사전
| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | Product | 메뉴에 등록될 음식을 말한다 | 
| 상품명 | Displayed Name | 상품명을 말한다 | 
| 비속어 | Slang | 비속어 정책에 정의된 사이트에서 필터링 된 단어 |
| 가격 | Price | 상품 가격을 말한다 |
| 상품 등록 | Register Product | (새로운)상품을 등록하는 것을 말한다 | 
| 상품 가격 변경 | Change the Price | 상품 가격을 변경하는 것을 말한다 | 
| 상품 목록 조회 | View All Products | 전체 상품을 조회하는 것을 말한다 | 
