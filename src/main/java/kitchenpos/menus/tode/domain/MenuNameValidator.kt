package kitchenpos.menus.tode.domain

import kitchenpos.products.tobe.port.ProductNameSlangChecker
import org.springframework.stereotype.Component

@Component
class MenuNameValidator(
    private val slangChecker: ProductNameSlangChecker,
) {
    fun requireNormalName(
        name: String?,
    ) = require(!(name == null || isContainProfanity(name)))

    private fun isContainProfanity(
        name: String,
    ) = slangChecker.containSlang(name)
}
