package kitchenpos.menus.tobe.application

import kitchenpos.menus.tobe.application.dto.CreateMenuRequest
import kitchenpos.menus.tobe.domain.TobeMenuGroupRepository
import kitchenpos.menus.tobe.domain.TobeMenuRepository
import kitchenpos.menus.tobe.domain.menu.TobeMenu
import kitchenpos.menus.tobe.domain.menuproduct.TobeMenuProduct
import kitchenpos.shared.domain.DefaultProfanities
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TobeMenuService(
    private val tobeMenuRepository: TobeMenuRepository,
    private val tobeMenuGroupRepository: TobeMenuGroupRepository,
) {
    /**
     * TODO :
     * 1. menuProducts validation (w/ domain service -> product 접근)
     * 2. displayed constructor 에 추가
     */
    @Transactional
    fun create(request: CreateMenuRequest): TobeMenu {
        val groupId = request.groupId
        if (!tobeMenuGroupRepository.existsById(request.groupId)) {
            throw NoSuchElementException()
        }
        val name = request.name
        val price = request.price
        val menuProducts = request.menuProducts.map { TobeMenuProduct(it.quantity, it.price, it.productId) }
        val displayed = request.displayed
        val profanities = DefaultProfanities()
        val menu = TobeMenu(name, price, profanities, groupId, menuProducts)
        return tobeMenuRepository.save(menu)
    }
}
