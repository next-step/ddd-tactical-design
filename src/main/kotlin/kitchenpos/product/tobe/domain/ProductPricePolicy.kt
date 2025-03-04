package kitchenpos.product.tobe.domain

import java.math.BigDecimal

class ProductPricePolicy {
    fun validate(price: BigDecimal) {
        require(price >= BigDecimal.ZERO) { "상품 가격은 0원 이상이어야 합니다." }
    }
}
