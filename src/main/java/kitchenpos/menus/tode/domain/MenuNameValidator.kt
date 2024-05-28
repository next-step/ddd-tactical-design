package kitchenpos.menus.tode.domain

import kitchenpos.menus.tode.port.MenuNameSlangChecker
import org.springframework.stereotype.Component

@Component
class MenuNameValidator(
    private val menuNameSlangChecker: MenuNameSlangChecker,
) {
    fun requireNormalName(
        name: String?,
    ) = require(!(name == null || isContainProfanity(name)))

    private fun isContainProfanity(
        name: String,
    ) = menuNameSlangChecker.containSlang(name)
}
