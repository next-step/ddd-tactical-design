package kitchenpos.tobe.menu.domain.repository

import kitchenpos.tobe.menu.domain.entity.MenuV2
import java.util.*

interface MenuRepositoryV2 {
    fun findAllByProductId(productId: UUID): List<MenuV2>

    fun findAll(): List<MenuV2>

    fun findMenuById(id: UUID): MenuV2?

    fun save(menuV2: MenuV2): MenuV2

    fun findAllByIdIn(ids: List<UUID>): List<MenuV2>
}
