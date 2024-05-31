package kitchenpos.tobe.menu.domain.repository

import kitchenpos.tobe.menu.domain.entity.MenuGroupV2
import java.util.UUID

interface MenuGroupRepositoryV2 {
    fun findMenuGroupById(id: UUID): MenuGroupV2?

    fun save(menuGroup: MenuGroupV2): MenuGroupV2
}
