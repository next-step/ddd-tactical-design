package kitchenpos.order.tobe.common

import java.util.*
import kitchenpos.menu.tobe.domain.MenuDisplay

data class OrderMenuInfo(
    val menuId: UUID,
    val menuDisplay: MenuDisplay,
)
