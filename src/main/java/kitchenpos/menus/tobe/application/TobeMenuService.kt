package kitchenpos.menus.tobe.application

import kitchenpos.menus.tobe.application.dto.CreateMenuRequest
import kitchenpos.menus.tobe.domain.TobeMenuGroupRepository
import kitchenpos.menus.tobe.domain.TobeMenuRepository
import kitchenpos.menus.tobe.domain.menu.TobeMenu
import kitchenpos.shared.domain.DefaultProfanities
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TobeMenuService(
    private val tobeMenuRepository: TobeMenuRepository,
    private val tobeMenuGroupRepository: TobeMenuGroupRepository,
) {
    @Transactional
    fun create(request: CreateMenuRequest): TobeMenu? {
        val menuGroup =
            tobeMenuGroupRepository.findById(request.groupId)
                .orElseThrow { NoSuchElementException() }
        val name = request.name
        val price = request.price
        // TODO : menuProducts validation (w/ domain service -> product 접근)
        val menuProducts = request.menuProducts
        // TODO : displayed constructor 에 추가
        val displayed = request.displayed
        val profanities = DefaultProfanities()
        val menu = TobeMenu(name, price, profanities, menuGroup, menuProducts)
        return tobeMenuRepository.save(menu)
    }
}
