package kitchenpos.product.tobe.domain

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

/**
#### 속성
- `Product`는 식별자와 `price`, `name`을 가진다.
#### 공통 정책
- `Product`의 `name`은 필수값이고, `Profanities`를 통해 `Profanity`가 포함되어 있지 않은지 확인한다.
- `Product`의 `price`는 0원 이상이어야 한다.
#### 기능
- `Product`를 등록
- `Product`를 전체조회
- `Product`의 `price`를 변경
- `Product`를 포함한 `Menu`들 중  `MenuPrice <= ProductPrice * MenuProductQuantity`를 만족하지 못하는 `Menu`는 `Not Displayed`된다
 */
@Table(name = "product")
@Entity
class Product(
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    var id: UUID? = null,
    @Embedded
    var productName: ProductName,
    @Column(name = "price", nullable = false)
    var price: BigDecimal,
) {

    init {
        require(price >= BigDecimal.ZERO) { "상품 가격은 0원 이상이어야 합니다." }
    }


}
