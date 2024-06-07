package kitchenpos.menus.tobe.application.dto

import java.util.UUID

data class CreateMenuRequest(
    val name: String,
    val price: Int,
    val groupId: UUID,
    val displayed: Boolean,
    val menuProducts: List<MenuProductRequest>,
)

data class MenuProductRequest(
    val quantity: Int,
    val price: Int,
    val productId: UUID,
)
