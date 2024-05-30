package kitchenpos.tobe.menu.infra

import kitchenpos.tobe.menu.application.MenuServiceV2
import kitchenpos.tobe.product.domain.event.ProductPriceChangedEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Service
class MenuEventHandler(
    private val menuService: MenuServiceV2,
) {
    @Async
    @TransactionalEventListener(
        classes = [ProductPriceChangedEvent::class],
        phase = TransactionPhase.AFTER_COMMIT,
    )
    fun handle(event: ProductPriceChangedEvent) {
        menuService.handleProductPriceChanged(event)
    }
}
