package kitchenpos.products.tobe.domain

@JvmInline
value class ProductName private constructor(val value: String) {
    companion object {
        fun of(name: String, productNameValidator: ProductNameValidator): ProductName {
            productNameValidator.validate(name)

            return ProductName(name)
        }
    }
}
