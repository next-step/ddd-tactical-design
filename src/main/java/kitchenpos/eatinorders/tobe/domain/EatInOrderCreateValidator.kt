package kitchenpos.eatinorders.tobe.domain

import java.util.*

interface EatInOrderCreateValidator {
    fun validate(orderTableId: UUID, eatInOrderLineItems: EatInOrderLineItems)
}
