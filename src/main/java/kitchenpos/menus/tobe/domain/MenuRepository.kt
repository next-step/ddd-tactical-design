package kitchenpos.menus.tobe.domain

import java.util.*

fun MenuRepository.findByIdOrNull(menuId: UUID): Menu? = findById(menuId).orElse(null)

interface MenuRepository {
    fun save(menu: Menu): Menu

    fun findAllByProductId(productId: UUID): List<Menu>

    fun findById(menuId: UUID): Optional<Menu>

    fun findByIdIn(menuIds: List<UUID>): List<Menu>
}
