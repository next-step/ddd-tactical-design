package kitchenpos.menus.tobe.domain

class BlackWords(
    private val words: List<String>,
) {
    fun contains(target: String): Boolean {
        return words.any { target.contains(it) }
    }
}
