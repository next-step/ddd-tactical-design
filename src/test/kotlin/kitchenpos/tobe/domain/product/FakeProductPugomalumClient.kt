package kitchenpos.tobe.domain.product

import kitchenpos.tobe.product.domain.ProductPurgomalumClient

class FakeProductPugomalumClient : ProductPurgomalumClient {
    private val profanityWords = setOf("욕1", "욕2", "욕3")

    override fun containsProfanity(text: String): Boolean {
        return profanityWords.any { text.contains(it) }
    }
}
