package kitchenpos.tobe.product.exception

class ProductPriceException(
    override val message: String?,
) : IllegalArgumentException(message)
