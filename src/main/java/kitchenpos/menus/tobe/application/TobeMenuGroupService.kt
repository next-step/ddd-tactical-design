package kitchenpos.menus.tobe.application

import kitchenpos.menus.tobe.application.dto.CreateMenuGroupRequest
import kitchenpos.menus.tobe.application.dto.MenuGroupResponse
import kitchenpos.menus.tobe.domain.TobeMenuGroupRepository
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroup
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class TobeMenuGroupService(
    private val tobeMenuGroupRepository: TobeMenuGroupRepository,
) {
    @Transactional
    fun create(request: CreateMenuGroupRequest): MenuGroupResponse {
        val id = request.id
        val name = request.name
        val menuGroup = TobeMenuGroup(id, name)
        val savedMenuGroup = tobeMenuGroupRepository.save(menuGroup)

        return savedMenuGroup.toDto()
    }

    @Transactional(readOnly = true)
    fun findAll(): List<MenuGroupResponse> {
        val menuGroups = tobeMenuGroupRepository.findAll()
        return menuGroups.map { it.toDto() }
    }
}
