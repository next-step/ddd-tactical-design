package kitchenpos.menus.tobe.application

import kitchenpos.menus.tobe.application.dto.ChangePriceRequest
import kitchenpos.menus.tobe.application.dto.CreateMenuRequest
import kitchenpos.menus.tobe.domain.TobeMenuRepository
import kitchenpos.menus.tobe.domain.menu.TobeMenu
import kitchenpos.menus.tobe.domain.menu.TobeMenuFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.NoSuchElementException

@Service
class TobeMenuService(
    private val tobeMenuRepository: TobeMenuRepository,
    private val tobeMenuFactory: TobeMenuFactory,
) {
    @Transactional
    fun create(request: CreateMenuRequest): TobeMenu {
        val menu = tobeMenuFactory.createMenu(request)
        return tobeMenuRepository.save(menu)
    }

    @Transactional
    fun changePrice(request: ChangePriceRequest): TobeMenu {
        val menu =
            tobeMenuRepository.findById(request.menuId)
                .orElseThrow { NoSuchElementException() }
        menu.changePrice(request.price)
        return menu
    }

    @Transactional
    fun display(menuId: UUID): TobeMenu {
        val menu =
            tobeMenuRepository.findById(menuId)
                .orElseThrow { NoSuchElementException() }
        menu.display()
        return menu
    }

    @Transactional
    fun hide(menuId: UUID): TobeMenu {
        val menu =
            tobeMenuRepository.findById(menuId)
                .orElseThrow { NoSuchElementException() }
        menu.hide()
        return menu
    }

    @Transactional(readOnly = true)
    fun findAll(): List<TobeMenu> {
        return tobeMenuRepository.findAll()
    }
}
