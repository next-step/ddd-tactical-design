package kitchenpos.tobe.product.exception.menu

class MenuNameException(
    override val message: String?,
) : IllegalArgumentException(message)
