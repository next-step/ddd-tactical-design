package kitchenpos.menus.tode.domain

import kitchenpos.menus.domain.MenuProduct

object MenuProductValidator {
    fun requireNormalMenuProduct(
        menuProductRequest: List<MenuProduct>?
    ) = require(!menuProductRequest.isNullOrEmpty())
}
