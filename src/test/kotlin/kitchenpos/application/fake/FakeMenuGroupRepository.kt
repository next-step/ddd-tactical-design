package kitchenpos.application.fake

import kitchenpos.menu.domain.MenuGroup
import kitchenpos.menu.domain.MenuGroupRepository
import java.util.*

class FakeMenuGroupRepository : MenuGroupRepository {
    private val menuGroups = mutableMapOf<UUID, MenuGroup>()

    override fun save(entity: MenuGroup): MenuGroup {
        menuGroups[entity.id] = entity
        return entity
    }

    override fun findById(id: UUID): Optional<MenuGroup> {
        return Optional.ofNullable(menuGroups[id])
    }

    override fun findAll(): MutableList<MenuGroup> {
        return menuGroups.values.toMutableList()
    }
}
