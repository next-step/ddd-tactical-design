package kitchenpos.products.tobe.domain

import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class ProductPriceValidator {
    fun requireNormalPrice(
        price: BigDecimal?,
    ) = require(price != null && isNotNegativePrice(price))

    private fun isNotNegativePrice(
        price: BigDecimal,
    ) = price >= BigDecimal.ZERO
}