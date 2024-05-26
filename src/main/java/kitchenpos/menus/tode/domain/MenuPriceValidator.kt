package kitchenpos.menus.tode.domain

import java.math.BigDecimal

object MenuPriceValidator {
    fun requireNormalPrice(
        price: BigDecimal?,
    ) = require(!(price == null || isNegativePrice(price)))

    fun requireMenuPriceUnderSum(
        menuPrice: BigDecimal,
        sum: BigDecimal,
    ) = require(menuPrice <= sum)

    private fun isNegativePrice(
        price: BigDecimal,
    ) = price < BigDecimal.ZERO
}
