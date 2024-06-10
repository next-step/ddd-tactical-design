package kitchenpos.eatinorders.tobe

import kitchenpos.eatinorders.tobe.domain.EatInOrderCreateValidator
import kitchenpos.eatinorders.tobe.domain.EatInOrderLineItems
import java.util.*

object AlwaysSuccessEatInOrderCreateValidator : EatInOrderCreateValidator {
    override fun validate(orderTableId: UUID, eatInOrderLineItems: EatInOrderLineItems) {}
}
