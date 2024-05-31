package kitchenpos.tobe.product.exception.menu

class MenuPriceException(
    override val message: String?,
) : IllegalArgumentException(message)
