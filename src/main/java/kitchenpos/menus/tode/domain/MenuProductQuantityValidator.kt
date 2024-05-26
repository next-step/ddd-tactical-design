package kitchenpos.menus.tode.domain

object MenuProductQuantityValidator {
    fun requireNotNegative(
        quantity: Long,
    ) = require(quantity >= 0)
}
