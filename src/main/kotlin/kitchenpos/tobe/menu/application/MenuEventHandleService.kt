package kitchenpos.tobe.menu.application

import kitchenpos.tobe.menu.domain.MenuDisplayChecker
import kitchenpos.tobe.product.domain.event.ProductPriceChangedEvent
import org.springframework.stereotype.Service

@Service
class MenuEventHandleService(
    private val menuDisplayChecker: MenuDisplayChecker,
) {
    fun handleProductPriceChanged(event: ProductPriceChangedEvent) {
        menuDisplayChecker.handleProductPriceChanged(event.productId, event.price)
    }
}
