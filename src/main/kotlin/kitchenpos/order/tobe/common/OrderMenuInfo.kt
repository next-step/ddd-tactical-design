package kitchenpos.order.tobe.common

import java.math.BigDecimal
import java.util.*

data class OrderMenuInfo(
    val menuId: UUID,
    val menuPrice: BigDecimal,
    val isDisplay: Boolean,
) {
}
