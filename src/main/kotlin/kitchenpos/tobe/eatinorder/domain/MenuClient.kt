package kitchenpos.tobe.eatinorder.domain

import kitchenpos.tobe.eatinorder.domain.dto.MenuData
import java.util.UUID

interface MenuClient {
    fun getMenus(menuIds: List<UUID>): List<MenuData>
}
