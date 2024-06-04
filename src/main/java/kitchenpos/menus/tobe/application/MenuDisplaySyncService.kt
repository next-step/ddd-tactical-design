package kitchenpos.menus.tobe.application

import kitchenpos.menus.tobe.domain.Menu
import kitchenpos.menus.tobe.domain.MenuPriceValidator
import kitchenpos.menus.tobe.domain.MenuRepository
import kitchenpos.products.domain.ProductPriceChanged
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Service
class MenuDisplaySyncService(
    private val menuRepository: MenuRepository,
    private val menuPriceValidator: MenuPriceValidator
) {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun syncMenusDisplayStatus(productPriceChanged: ProductPriceChanged) {
        val menus = menuRepository.findAllByProductId(productPriceChanged.productId)

        menus.forEach { syncMenuDisplayStatus(it) }
    }

    private fun syncMenuDisplayStatus(menu: Menu) {
        if (!menuPriceValidator.isValid(menu)) {
            menu.inActivateDisplayStatus()
        }
    }
}
