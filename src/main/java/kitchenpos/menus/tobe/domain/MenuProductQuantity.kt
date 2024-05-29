package kitchenpos.menus.tobe.domain

@JvmInline
value class MenuProductQuantity(
    val value: Long
) {
    init {
        if (value < 0) {
            throw IllegalArgumentException("메뉴의 상품 수량은 0보다 크거나 같아야합니다")
        }
    }
}
