package kitchenpos.menu.tobe.application

import kitchenpos.menu.tobe.domain.MenuRepository
import kitchenpos.menu.tobe.domain.ProductClient
import kitchenpos.product.tobe.domain.ProductPriceChangedEvent
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Service
class MenuProductPriceChangedListener(
    private val menuRepository: MenuRepository,
    private val productClient: ProductClient,
) {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handle(event: ProductPriceChangedEvent) {
        val menus = menuRepository.findAllByProductId(event.productId)
        menus.forEach {
            val productInfos = productClient.getProducts(it.productIds())
            if (!it.canDisplay(productInfos)) {
                it.notDisplay()
            }
        }
    }
}
