package kitchenpos.menus.tobe.application.dto

import java.util.*

data class CreateMenuGroupRequest(
    val id: UUID = UUID.randomUUID(),
    val name: String,
)
