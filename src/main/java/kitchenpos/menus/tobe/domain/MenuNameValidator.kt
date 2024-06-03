package kitchenpos.menus.tobe.domain

fun interface MenuNameValidator {
    fun validate(name: String): Boolean
}
