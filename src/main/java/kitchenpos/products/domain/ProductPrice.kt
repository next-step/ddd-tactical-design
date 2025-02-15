package kitchenpos.products.domain

import java.math.BigDecimal

class ProductPrice private constructor(
    val price: BigDecimal
) {
    companion object Factory {
        fun create(value: BigDecimal): ProductPrice {
            validateNull(value)
            validatePositive(value)
            return ProductPrice(value)
        }

        private fun validateNull(value: BigDecimal) {
            if (value <= BigDecimal.ZERO) {
                throw IllegalArgumentException("Invalid product price: $value")
            }
        }

        private fun validatePositive(value: BigDecimal) {
            if (value < BigDecimal.ZERO) {
                throw IllegalArgumentException("Invalid product price: $value")
            }
        }
    }
}
