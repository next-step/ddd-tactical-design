package kitchenpos.menus.tobe.infra

class FakePurgomalumClient(vararg word: String) : PurgomalumClient {
    private val profanities: List<String> = word.toList()

    override fun containsProfanity(text: String): Boolean {
        return profanities.stream()
            .anyMatch { profanity: String? -> text.contains(profanity!!) }
    }
}
