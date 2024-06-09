package kitchenpos.menus.tobe.application

import kitchenpos.menus.tobe.application.dto.CreateMenuRequest
import kitchenpos.menus.tobe.domain.TobeMenuRepository
import kitchenpos.menus.tobe.domain.menu.TobeMenu
import kitchenpos.menus.tobe.domain.menu.TobeMenuFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TobeMenuService(
    private val tobeMenuRepository: TobeMenuRepository,
    private val tobeMenuFactory: TobeMenuFactory,
) {
    /**
     * TODO :
     * 1. menuProducts validation (w/ domain service -> product 접근)
     */
    @Transactional
    fun create(request: CreateMenuRequest): TobeMenu {
        val menu = tobeMenuFactory.createMenu(request)
        return tobeMenuRepository.save(menu)
    }
}
