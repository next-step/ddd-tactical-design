package kitchenpos.product.tobe.domain

class FakeProfanities(
    private val profanities: List<String> = emptyList()
) : Profanities {

    override fun contains(profanity: String): Boolean {
        return profanities.any { profanity.contains(it) }
    }
}
