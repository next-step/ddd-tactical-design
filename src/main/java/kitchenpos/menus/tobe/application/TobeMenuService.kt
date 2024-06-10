package kitchenpos.menus.tobe.application

import kitchenpos.menus.tobe.application.dto.ChangePriceRequest
import kitchenpos.menus.tobe.application.dto.CreateMenuRequest
import kitchenpos.menus.tobe.application.dto.TobeMenuResponse
import kitchenpos.menus.tobe.domain.TobeMenuRepository
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
    fun create(request: CreateMenuRequest): TobeMenuResponse {
        val menu = tobeMenuFactory.createMenu(request)
        val savedMenu = tobeMenuRepository.save(menu)
        return savedMenu.toResponseDto()
    }

    @Transactional
    fun changePrice(request: ChangePriceRequest): TobeMenuResponse {
        val menu =
            tobeMenuRepository.findById(request.menuId)
                .orElseThrow { NoSuchElementException() }
        menu.changePrice(request.price)
        return menu.toResponseDto()
    }

    @Transactional
    fun display(menuId: UUID): TobeMenuResponse {
        val menu =
            tobeMenuRepository.findById(menuId)
                .orElseThrow { NoSuchElementException() }
        menu.display()
        return menu.toResponseDto()
    }

    @Transactional
    fun hide(menuId: UUID): TobeMenuResponse {
        val menu =
            tobeMenuRepository.findById(menuId)
                .orElseThrow { NoSuchElementException() }
        menu.hide()
        return menu.toResponseDto()
    }

    @Transactional(readOnly = true)
    fun findAll(): List<TobeMenuResponse> {
        val menus = tobeMenuRepository.findAll()
        return menus.map { it.toResponseDto() }
    }
}
