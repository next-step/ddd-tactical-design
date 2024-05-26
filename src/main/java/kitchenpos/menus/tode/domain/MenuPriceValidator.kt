package kitchenpos.menus.tode.domain

import java.math.BigDecimal

object MenuPriceValidator {
    fun requireNormalPrice(
        price: BigDecimal?,
    ) = require(price != null && isNotNegativePrice(price))

    private fun isNotNegativePrice(
        price: BigDecimal,
    ) = price >= BigDecimal.ZERO
}
