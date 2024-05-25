package kitchenpos.products.tobe.domain

import java.math.BigDecimal

@JvmInline
value class ProductPrice(
    val value: BigDecimal
) {
    init {
        require(value >= BigDecimal.ZERO) { "상품의 가격은 0보다 작을 수 없습니다" }
    }
}
