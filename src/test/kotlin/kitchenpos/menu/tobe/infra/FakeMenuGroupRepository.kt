package kitchenpos.menu.tobe.infra

import java.util.*
import kitchenpos.menu.tobe.domain.MenuGroup
import kitchenpos.menu.tobe.domain.MenuGroupRepository

class FakeMenuGroupRepository : MenuGroupRepository {
    private val menuGroups: MutableMap<UUID, MenuGroup> = mutableMapOf()

    override fun save(menuGroup: MenuGroup): MenuGroup {
        menuGroup.id = UUID.randomUUID()
        menuGroups[menuGroup.id!!] = menuGroup
        return menuGroup
    }

    override fun findById(id: UUID): Optional<MenuGroup> {
        return Optional.ofNullable(menuGroups[id])
    }

    override fun findAll(): List<MenuGroup> {
        return menuGroups.values.toList()
    }
}
