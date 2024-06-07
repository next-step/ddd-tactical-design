package kitchenpos.shared.domain

class DefaultProfanities : Profanities {
    private val profanities =
        setOf<String>(
            "비속어",
        )

    override fun contains(text: String?): Boolean {
        return profanities.contains(text)
    }
}
