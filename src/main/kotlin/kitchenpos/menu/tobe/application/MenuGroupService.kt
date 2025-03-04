package kitchenpos.menu.tobe.application

import kitchenpos.menu.tobe.application.dto.CreateMenuGroupReq
import kitchenpos.menu.tobe.application.dto.MenuGroupResp
import kitchenpos.menu.tobe.domain.MenuGroup
import kitchenpos.menu.tobe.domain.MenuGroupRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service(value = "tobeMenuGroupService")
class MenuGroupService(
    private val menuGroupRepository: MenuGroupRepository,
) {

    @Transactional
    fun create(request: CreateMenuGroupReq): MenuGroupResp {
        val menuGroup = MenuGroup(
            name = request.name
        )
        menuGroupRepository.save(menuGroup)
        return MenuGroupResp.of(menuGroup)
    }

    fun findAll(): List<MenuGroupResp> {
        return menuGroupRepository.findAll()
            .map(MenuGroupResp::of)
    }
}
