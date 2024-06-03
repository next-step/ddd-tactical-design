package kitchenpos.menus.tobe.domain

class MenuName(val value: String, nameValidator: MenuNameValidator) {
    init {
        require(value.isNotEmpty()) { "메뉴 그룹 이름은 비어있을 수 없습니다." }
        require(nameValidator.validate(value)) { "금지어가 포함되어 있습니다." }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MenuName) return false

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}
