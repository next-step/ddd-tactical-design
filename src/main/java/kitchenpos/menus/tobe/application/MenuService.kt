package kitchenpos.menus.tobe.application

import kitchenpos.common.Price
import kitchenpos.menus.tobe.domain.*
import kitchenpos.menus.tobe.dto.`in`.MenuCreateRequest
import kitchenpos.menus.tobe.dto.out.MenuResponse
import kitchenpos.menus.tobe.dto.out.fromMenu
import kitchenpos.products.domain.ProductPriceChanged
import kitchenpos.products.tobe.domain.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import java.util.*
import kotlin.NoSuchElementException

@Service
class MenuService(
    private val menuRepository: MenuRepository,
    private val menuGroupRepository: MenuGroupRepository,
    private val menuNameValidator: MenuNameValidator,
    private val productRepository: ProductRepository
) {

    @Transactional
    fun createMenu(request: MenuCreateRequest): MenuResponse {
        val menuGroup = menuGroupRepository.findByIdOrNull(request.menuGroupId)
            ?: throw NoSuchElementException("can not found menu group : ${request.menuGroupId}")

        val menuProducts = createMenuProducts(request)

        val menu = Menu.of(
            menuGroup = menuGroup,
            name = MenuName.of(request.name, menuNameValidator),
            price = request.price,
            displayStatus = request.displayed,
            menuProducts = menuProducts,
        )

        return fromMenu(menuRepository.save(menu))
    }

    private fun createMenuProducts(request: MenuCreateRequest): MenuProducts {
        val productMap = productRepository.findAllByIdIn(request.productIds)
            .associateBy { it.id }

        return request.menuProducts
            .map {
                val price = productMap[it.productId]?.price
                    ?: throw NoSuchElementException("can not found product: ${it.productId}")

                MenuProduct(it.productId, MenuProductQuantity(it.quantity), price)
            }
            .let { MenuProducts(it) }
    }

    @Transactional
    fun changeMenuPrice(menuId: UUID, price: Price) {
        val menu = menuRepository.findByIdOrNull(menuId) ?: throw NoSuchElementException("can not found menu: $menuId")

        menu.changePrice(price)
    }

    @Transactional
    fun activateMenuDisplayStatus(menuId: UUID) {
        val menu = menuRepository.findByIdOrNull(menuId) ?: throw NoSuchElementException("can not found menu: $menuId")

        menu.activateDisplayStatus()
    }

    @Transactional
    fun inActivateMenuDisplayStatus(menuId: UUID) {
        val menu = menuRepository.findByIdOrNull(menuId) ?: throw NoSuchElementException("can not found menu: $menuId")

        menu.inActivateDisplayStatus()
    }
}
