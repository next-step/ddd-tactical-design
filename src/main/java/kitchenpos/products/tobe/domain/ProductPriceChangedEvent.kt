package kitchenpos.products.tobe.domain

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

data class ProductPriceChangedEvent(
    val productId: UUID,
    val oldPrice: BigDecimal,
    val newPrice: BigDecimal,
    override val occurredOn: LocalDateTime = LocalDateTime.now(),
) : DomainEvent
