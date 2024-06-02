package kitchenpos.tobe.menu.infra

import kitchenpos.tobe.menu.application.MenuEventHandleService
import kitchenpos.tobe.product.domain.event.ProductPriceChangedEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Service
class MenuEventHandler(
    private val menuEventHandleService: MenuEventHandleService,
) {
    @Async
    @TransactionalEventListener(
        classes = [ProductPriceChangedEvent::class],
        phase = TransactionPhase.AFTER_COMMIT,
    )
    fun handle(event: ProductPriceChangedEvent) {
        menuEventHandleService.handleProductPriceChanged(event)
    }
}
