package kitchenpos.menu.tobe.infra

import kitchenpos.menu.tobe.domain.MenuAmountService
import kitchenpos.menu.tobe.domain.MenuProductPriceChangedEventListener
import kitchenpos.menu.tobe.domain.MenuRepository
import kitchenpos.product.tobe.domain.ProductPriceChangedEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class DefaultMenuProductPriceChangedEventListener(
    private val menuRepository: MenuRepository,
    private val amountService: MenuAmountService,
) : MenuProductPriceChangedEventListener {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    override fun handle(event: ProductPriceChangedEvent) {
        val menus = menuRepository.findAllByProductId(event.productId)
        menus.forEach {
            if (!it.canDisplay(amountService)) {
                it.notDisplay()
            }
        }
    }
}
