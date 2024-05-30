package kitchenpos.tobe.menu.application

import kitchenpos.tobe.menu.domain.MenuDisplayChecker
import kitchenpos.tobe.product.domain.event.ProductPriceChangedEvent
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MenuServiceV2(
    private val menuDisplayChecker: MenuDisplayChecker,
) {
    fun handleProductPriceChanged(event: ProductPriceChangedEvent) {
        menuDisplayChecker.unDisplayIfNeed(event.productId, event.price)
    }
}
