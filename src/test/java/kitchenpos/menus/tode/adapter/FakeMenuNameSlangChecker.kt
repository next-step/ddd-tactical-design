package kitchenpos.menus.tode.adapter

import kitchenpos.menus.tode.port.MenuNameSlangChecker
import kitchenpos.products.application.FakePurgomalumClient

class FakeMenuNameSlangChecker : MenuNameSlangChecker {
    private val fakePurgomalumClient = FakePurgomalumClient()

    override fun containSlang(text: String): Boolean = fakePurgomalumClient.containsProfanity(text)
}
