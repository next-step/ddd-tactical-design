package kitchenpos.products.domain

import kitchenpos.products.infra.checkBadWordClient

class ProductName private constructor(
    val name: String,
) {
    companion object Factory {
        fun create(value: String, checkBadWordClient: checkBadWordClient): ProductName {
            validateNull(value)
            validateProfanity(value, checkBadWordClient)
            return ProductName(value)
        }

        private fun validateNull(value: String) {
            if (value.isBlank()) {
                throw IllegalArgumentException("Invalid product name: $value")
            }
        }

        private fun validateProfanity(value: String, profanityCheckClient: checkBadWordClient) {
            if (profanityCheckClient.containsProfanity(value)) {
                throw IllegalArgumentException("Profanity is not allowed: $value")
            }
        }
    }
}
