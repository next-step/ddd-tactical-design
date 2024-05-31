package kitchenpos.menus.tobe.application

import kitchenpos.menus.tobe.domain.MenuGroup
import kitchenpos.menus.tobe.domain.MenuGroupName
import kitchenpos.menus.tobe.domain.MenuGroupRepository
import kitchenpos.menus.tobe.dto.out.MenuGroupResponse
import kitchenpos.menus.tobe.dto.out.fromMenuGroup
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MenuGroupService(
    private val menuGroupRepository: MenuGroupRepository
) {

    @Transactional
    fun createMenuGroup(name: String): MenuGroupResponse {
        val menuGroup = MenuGroup(MenuGroupName(name))

        return fromMenuGroup(menuGroupRepository.save(menuGroup))
    }

}
