package kitchenpos.menus.tobe.domain

@JvmInline
value class MenuGroupName(
    val value: String
) {
    init {
        require(value.isNotEmpty()) {"menu group 이름은 공백일 수 없습니다"}
    }
}
