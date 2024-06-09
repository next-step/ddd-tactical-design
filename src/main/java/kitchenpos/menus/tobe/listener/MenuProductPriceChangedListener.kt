package kitchenpos.menus.tobe.listener

import kitchenpos.menus.tobe.domain.MenuRepository
import kitchenpos.products.tobe.event.ProductPriceChanged
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Service
class MenuProductPriceChangedListener(
    private val menuRepository: MenuRepository,
) {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handle(productPriceChanged: ProductPriceChanged) {
        val menus = menuRepository.findAllByProductId(productPriceChanged.productId)

        menus.forEach { it.changeMenuProduct(productPriceChanged.productId, productPriceChanged.price) }
    }
}
