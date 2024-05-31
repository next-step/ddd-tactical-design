package kitchenpos.tobe.product.exception.product

class ProductPriceException(
    override val message: String?,
) : IllegalArgumentException(message)
