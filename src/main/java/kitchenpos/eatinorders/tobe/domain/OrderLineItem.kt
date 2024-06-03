package kitchenpos.eatinorders.tobe.domain

import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded

@Embeddable
class OrderLineItem(
    @Embedded
    val orderMenu: OrderMenu,
    val quantity: Int,
)
