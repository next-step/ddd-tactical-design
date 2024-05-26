package kitchenpos.products.tobe.domain

import kitchenpos.common.Price
import kitchenpos.common.ZERO

@JvmInline
value class ProductPrice(
    val value: Price
) {
    init {
        require(value >= ZERO) { "상품의 가격은 0보다 작을 수 없습니다" }
    }
}
fun Price.productPrice() = ProductPrice(this)
