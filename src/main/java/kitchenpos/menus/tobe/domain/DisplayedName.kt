package kitchenpos.menus.tobe.domain

@JvmInline
value class DisplayedName(val value: String) {
    init {
        require(value.isNotEmpty()) {
            "메뉴 그룹 이름은 비어있을 수 없습니다."
        }
    }
}
