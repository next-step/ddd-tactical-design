package kitchenpos.menu.tobe.application.dto

import java.util.*
import kitchenpos.menu.tobe.domain.MenuDisplay

data class CreateMenuReq(
    val name: String,
    val price: Long,
    val menuGroupId: UUID,
    val display: MenuDisplay,
    val menuProducts: List<CreateMenuProductReq>
)
