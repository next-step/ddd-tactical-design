package kitchenpos.products.tobe.application

import kitchenpos.products.tobe.dto.PriceChangeEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class PriceChangeEventPublisher(
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    fun publishPriceChange(event: PriceChangeEvent) {
        applicationEventPublisher.publishEvent(event)
    }
}
