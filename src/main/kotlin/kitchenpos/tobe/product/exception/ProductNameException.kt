package kitchenpos.tobe.product.exception

class ProductNameException(
    override val message: String?,
) : IllegalArgumentException(message)
