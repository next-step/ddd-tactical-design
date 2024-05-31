package kitchenpos.tobe.domain.menu

import kitchenpos.tobe.menu.domain.MenuPurgomalumClient

class FakeMenuPugomalumClient : MenuPurgomalumClient {
    private val profanityWords = setOf("욕1", "욕2", "욕3")

    override fun containsProfanity(text: String): Boolean {
        return profanityWords.any { text.contains(it) }
    }
}
