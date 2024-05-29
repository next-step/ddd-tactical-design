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

@Service
class MenuService(
    private val menuRepository: MenuRepository,
    private val menuGroupRepository: MenuGroupRepository,
    private val menuNameValidatorService: MenuNameValidatorService,
    private val menuPriceValidatorService: MenuPriceValidatorService,
    private val productRepository: ProductRepository,
) {

    @Transactional
    fun createMenu(request: MenuCreateRequest): MenuResponse {
        val menuGroup = menuGroupRepository.findByIdOrNull(request.menuGroupId)
            ?: throw NoSuchElementException("can not found menu group")

        val menuProducts = request.menuProducts
            .map {
                MenuProduct(it.productId, MenuProductQuantity(it.quantity))
            }.let { MenuProducts(it) }

        val menu = Menu(
            menuGroup = menuGroup,
            name = request.name,
            price = request.price,
            displayStatus = request.displayed,
            menuProducts = menuProducts,
            menuNameValidatorService,
            menuPriceValidatorService
        )

        return fromMenu(menuRepository.save(menu))
    }

    @Transactional
    fun changeMenuPrice(menuId: UUID, price: Price) {
        val menu = menuRepository.findByIdOrNull(menuId) ?: throw NoSuchElementException("can not found menu")

        menu.inActivateDisplayStatus()
    }

    @Transactional
    fun activateMenuDisplayStatus(menuId: UUID) {
        val menu = menuRepository.findByIdOrNull(menuId) ?: throw NoSuchElementException("can not found menu")

        menu.activateDisplayStatus(menuPriceValidatorService)
    }

    @Transactional
    fun inActivateMenuDisplayStatus(menuId: UUID) {
        val menu = menuRepository.findByIdOrNull(menuId) ?: throw NoSuchElementException("can not found menu")

        menu.inActivateDisplayStatus()
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun syncMenusDisplayStatus(productPriceChanged: ProductPriceChanged) {
        val menus = menuRepository.findAllByProductId(productPriceChanged.productId)

        menus.forEach { syncMenuDisplayStatus(it) }
    }

    private fun syncMenuDisplayStatus(menu: Menu) =
        runCatching {
            menuPriceValidatorService.validate(menu)
        }.onFailure { menu.inActivateDisplayStatus() }
}
