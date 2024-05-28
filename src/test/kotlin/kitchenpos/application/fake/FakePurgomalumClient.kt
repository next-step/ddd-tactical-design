package kitchenpos.application.fake

import kitchenpos.product.application.PurgomalumClient

class FakePurgomalumClient : PurgomalumClient {
    override fun containsProfanity(text: String): Boolean {
        return text.contains("비속어")
    }
}
