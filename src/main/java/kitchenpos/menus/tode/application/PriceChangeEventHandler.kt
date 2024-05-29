package kitchenpos.menus.tode.application

import kitchenpos.menus.domain.MenuRepository
import kitchenpos.menus.tode.domain.RenewMenuDisplay
import kitchenpos.products.tobe.dto.PriceChangeEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class PriceChangeEventHandler(
    private val menuRepository: MenuRepository,
) {

    @EventListener(classes = [PriceChangeEvent::class])
    fun priceChangeEventHandler(
        event: PriceChangeEvent,
    ) {
        menuRepository.findAllByProductId(event.productId)
            .forEach { menu ->
                RenewMenuDisplay.renewMenusDisplay(menu)
            }
    }
}
