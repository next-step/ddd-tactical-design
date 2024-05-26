package kitchenpos.menus.tode.domain

import kitchenpos.products.tobe.port.SlangChecker
import org.springframework.stereotype.Component

@Component
class MenuNameValidator(
    private val slangChecker: SlangChecker,
) {
    fun requireNormalName(
        name: String?,
    ) = require(name != null && isNotContainProfanity(name))

    private fun isNotContainProfanity(
        name: String,
    ) = !(slangChecker.containSlang(name))
}
