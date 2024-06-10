package kitchenpos.menus.tobe.application.dto

import java.util.UUID

data class ChangePriceRequest(
    val menuId: UUID,
    val price: Int,
)
