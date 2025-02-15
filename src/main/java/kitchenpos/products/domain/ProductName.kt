package kitchenpos.products.domain

class ProductName private constructor(
    val name: String,
) {
    companion object {
        fun create(value: String): ProductName {
            validateNull(value)
            return ProductName(value)
        }

        private fun validateNull(value: String) {
            if (value.isBlank()) {
                throw IllegalArgumentException("Invalid product name: $value")
            }
        }
    }
}
