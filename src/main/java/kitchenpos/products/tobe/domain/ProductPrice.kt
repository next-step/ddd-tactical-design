package kitchenpos.products.tobe.domain

import java.math.BigDecimal

private val MIN_PRICE = BigDecimal.ZERO

@JvmInline
value class ProductPrice(val value: BigDecimal) {
    init {
        require(value >= MIN_PRICE) {
            "ProductPrice 는 $MIN_PRICE 이상이어야 합니다."
        }
    }

    companion object {
        fun valueOf(value: Int): ProductPrice {
            return ProductPrice(value.toBigDecimal())
        }
    }
}
