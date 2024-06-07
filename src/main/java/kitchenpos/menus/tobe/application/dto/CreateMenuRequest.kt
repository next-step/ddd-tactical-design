package kitchenpos.menus.tobe.application.dto

import kitchenpos.menus.tobe.domain.menuproduct.TobeMenuProduct
import java.util.UUID

data class CreateMenuRequest(
    val name: String,
    val price: Int,
    val groupId: UUID,
    val displayed: Boolean,
    val menuProducts: List<TobeMenuProduct>,
)
