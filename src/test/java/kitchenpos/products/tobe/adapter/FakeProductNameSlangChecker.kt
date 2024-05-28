package kitchenpos.products.tobe.adapter

import kitchenpos.products.application.FakePurgomalumClient
import kitchenpos.products.tobe.port.ProductNameSlangChecker

class FakeProductNameSlangChecker : ProductNameSlangChecker {
    private val fakePurgomalumClient = FakePurgomalumClient()

    override fun containSlang(text: String): Boolean = fakePurgomalumClient.containsProfanity(text)
}