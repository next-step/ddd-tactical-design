package kitchenpos.menus.tobe.domain

import java.util.*

fun MenuGroupRepository.findByIdOrNull(menuGroupId: UUID): MenuGroup? =
    findById(menuGroupId).orElse(null)

interface MenuGroupRepository {
    fun findById(menuGroupId: UUID): Optional<MenuGroup>

    fun save(menuGroup: MenuGroup): MenuGroup
}
