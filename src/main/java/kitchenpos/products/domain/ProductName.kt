package kitchenpos.products.domain

import kitchenpos.profanity.ProfanityCheckClient

class ProductName private constructor(
    val name: String,
) {
    companion object Factory {
        fun create(value: String, profanityCheckClient: ProfanityCheckClient): ProductName {
            validateNull(value)
            validateProfanity(value, profanityCheckClient)
            return ProductName(value)
        }

        private fun validateNull(value: String) {
            if (value.isBlank()) {
                throw IllegalArgumentException("Invalid product name: $value")
            }
        }

        private fun validateProfanity(value: String, profanityCheckClient: ProfanityCheckClient) {
            if (profanityCheckClient.containsProfanity(value)) {
                throw IllegalArgumentException("Profanity is not allowed: $value")
            }
        }
    }
}
