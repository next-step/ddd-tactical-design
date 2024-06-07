package kitchenpos.menus.application.tobe

import kitchenpos.menus.tobe.domain.MenuGroup
import kitchenpos.menus.tobe.domain.MenuGroupRepository
import java.util.*


class FakeMenuGroupRepository : MenuGroupRepository {
    private val menuGroupMap: MutableMap<UUID, MenuGroup> = mutableMapOf()

    override fun findById(menuGroupId: UUID): Optional<MenuGroup> {
        return Optional.ofNullable(menuGroupMap[menuGroupId])
    }

    override fun save(menuGroup: MenuGroup): MenuGroup {
        menuGroupMap[menuGroup.id] = menuGroup

        return menuGroup
    }
}
