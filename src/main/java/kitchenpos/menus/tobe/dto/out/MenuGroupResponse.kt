package kitchenpos.menus.tobe.dto.out

import kitchenpos.menus.tobe.domain.MenuGroup
import java.util.UUID

data class MenuGroupResponse(
    val id: UUID,
    val name: String
)

fun fromMenuGroup(menuGroup: MenuGroup): MenuGroupResponse =
    MenuGroupResponse(
        id = menuGroup.id,
        name = menuGroup.name.value
    )
