package kitchenpos.menu.tobe.infra

import kitchenpos.common.annotation.DomainService
import kitchenpos.menu.tobe.domain.MenuAmountService
import kitchenpos.menu.tobe.domain.MenuProductPriceChangedListener
import kitchenpos.menu.tobe.domain.MenuRepository
import kitchenpos.product.tobe.domain.ProductPriceChangedEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@DomainService
class DefaultMenuProductPriceChangedListener(
    private val menuRepository: MenuRepository,
    private val amountService: MenuAmountService,
) : MenuProductPriceChangedListener {

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
