package kitchenpos.menu.tobe.domain

import java.math.BigDecimal

fun interface MenuAmountService {
    fun amount(menuProducts: MenuProducts): BigDecimal
}
