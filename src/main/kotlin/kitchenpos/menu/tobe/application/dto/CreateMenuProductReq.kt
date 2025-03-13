package kitchenpos.menu.tobe.application.dto

import java.util.*

data class CreateMenuProductReq(
    val seq: Long,
    val productId: UUID,
    val quantity: Long
)
