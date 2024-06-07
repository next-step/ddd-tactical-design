package kitchenpos.menus.tobe.domain

@JvmInline
value class MenuName private constructor(val value: String) {
    companion object {
        fun of(name: String, menuNameValidator: MenuNameValidator): MenuName {
            menuNameValidator.validate(name)

            return MenuName(name)
        }
    }
}
