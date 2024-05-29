package kitchenpos.products.tobe.domain

import kitchenpos.products.tobe.port.ProductNameSlangChecker
import org.springframework.stereotype.Component

@Component
class ProductNameValidator(
    private val productNameSlangChecker: ProductNameSlangChecker,
) {
    fun requireNormalName(
        name: String?,
    ) = require(name != null && isNotContainProfanity(name))

    private fun isNotContainProfanity(
        name: String,
    ) = !(productNameSlangChecker.containSlang(name))
}
