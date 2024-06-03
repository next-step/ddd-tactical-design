package kitchenpos.eatinorders.tobe.dto

import java.util.*

data class MenuValidateRequest(
    val menuId: UUID,
    val price: Int,
)
