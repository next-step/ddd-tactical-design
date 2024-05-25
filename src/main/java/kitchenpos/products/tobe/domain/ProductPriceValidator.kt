package kitchenpos.products.tobe.domain

import java.math.BigDecimal

object ProductPriceValidator {
    fun requireNormalPrice(
        price: BigDecimal?,
    ) = require(price != null && isNotNegativePrice(price))

    private fun isNotNegativePrice(
        price: BigDecimal,
    ) = price >= BigDecimal.ZERO
}
