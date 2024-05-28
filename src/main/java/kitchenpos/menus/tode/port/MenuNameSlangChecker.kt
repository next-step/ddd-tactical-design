package kitchenpos.menus.tode.port

interface MenuNameSlangChecker {
    fun containSlang(
        text: String,
    ): Boolean
}
