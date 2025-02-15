package kitchenpos.profanity

interface ProfanityCheckClient {
    fun containsProfanity(text: String?): Boolean
}
