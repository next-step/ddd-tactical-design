package kitchenpos.menus.application.tobe

import kitchenpos.menus.tobe.domain.Menu
import kitchenpos.menus.tobe.domain.MenuRepository
import java.util.*

class FakeMenuRepository : MenuRepository {
    private val menuMap: MutableMap<UUID, Menu> = mutableMapOf()

    override fun save(menu: Menu): Menu {
        menuMap[menu.id] = menu

        return menu
    }

    override fun findAllByProductId(productId: UUID): List<Menu> {
        return menuMap.filterValues { it.menuProducts.productIds.contains(productId) }
            .values.toList()
    }

    override fun findById(menuId: UUID): Optional<Menu> {
        return Optional.ofNullable(menuMap[menuId])
    }

    override fun findByIdIn(menuIds: List<UUID>): List<Menu> {
        return menuIds.mapNotNull { menuMap[it] }
    }
}
