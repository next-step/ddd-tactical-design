package kitchenpos.menu.tobe.application.dto

import java.util.*
import kitchenpos.menu.tobe.domain.MenuGroup

data class MenuGroupResp(
    val id: UUID,
    val name: String
) {
    companion object {
        fun of(menuGroup: MenuGroup): MenuGroupResp {
            return MenuGroupResp(
                id = menuGroup.id!!,
                name = menuGroup.name
            )
        }
    }
}
