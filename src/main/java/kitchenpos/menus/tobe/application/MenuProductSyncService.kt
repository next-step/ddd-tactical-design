package kitchenpos.menus.tobe.application

import kitchenpos.menus.tobe.domain.MenuRepository
import kitchenpos.products.domain.ProductPriceChanged
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Service
class MenuProductSyncService(
    private val menuRepository: MenuRepository,
) {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun syncMenusDisplayStatus(productPriceChanged: ProductPriceChanged) {
        val menus = menuRepository.findAllByProductId(productPriceChanged.productId)

        menus.forEach { it.changeMenuProduct(productPriceChanged.productId, productPriceChanged.price) }
    }
}
