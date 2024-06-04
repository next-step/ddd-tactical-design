package kitchenpos.menus.tobe.domain

interface MenuPriceValidator {
    fun validate(menu: Menu)

    fun isValid(menu: Menu): Boolean
}
