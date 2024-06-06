package kitchenpos.menus.tobe.application

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
    fun create(request: TobeMenuGroup): TobeMenuGroup {
        val id = UUID.randomUUID()
        val name = request.name
        val menuGroup = TobeMenuGroup(id, name.toString())
        return tobeMenuGroupRepository.save(menuGroup)
    }

    @Transactional(readOnly = true)
    fun findAll(): List<TobeMenuGroup> {
        return tobeMenuGroupRepository.findAll()
    }
}
