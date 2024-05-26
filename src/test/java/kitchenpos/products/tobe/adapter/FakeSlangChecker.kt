package kitchenpos.products.tobe.adapter

import kitchenpos.products.application.FakePurgomalumClient
import kitchenpos.products.tobe.port.SlangChecker

class FakeSlangChecker : SlangChecker {
    private val fakePurgomalumClient = FakePurgomalumClient()

    override fun containSlang(text: String): Boolean = fakePurgomalumClient.containsProfanity(text)
}