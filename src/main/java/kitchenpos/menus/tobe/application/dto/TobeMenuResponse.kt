package kitchenpos.menus.tobe.application.dto

import java.util.UUID

data class TobeMenuResponse(
    val id: UUID,
    val name: String,
    val price: Int,
    val isDisplayed: Boolean,
    val menuProducts: List<TobeMenuProductResponse>,
    val menuGroupId: UUID?,
)

data class TobeMenuProductResponse(
    val seq: Long,
    val quantity: Int,
    val price: Int,
    val productId: UUID?,
)
