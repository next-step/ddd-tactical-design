package kitchenpos.menu.tobe.infra

import kitchenpos.menu.tobe.domain.MenuProductPriceChanged
import kitchenpos.menu.tobe.domain.MenuProductPriceChangedEventListener
import kitchenpos.product.tobe.domain.ProductPriceChangedEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class DefaultMenuProductPriceChangedEventListener(
    private val menuProductPriceChanged: MenuProductPriceChanged
) : MenuProductPriceChangedEventListener {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    override fun handle(event: ProductPriceChangedEvent) {
        menuProductPriceChanged.changedProduct(event.productId)
    }
}
