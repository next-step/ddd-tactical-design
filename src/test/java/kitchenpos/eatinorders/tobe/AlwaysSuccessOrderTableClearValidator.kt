package kitchenpos.eatinorders.tobe

import kitchenpos.eatinorders.tobe.domain.OrderTable
import kitchenpos.eatinorders.tobe.domain.OrderTableClearValidator

object AlwaysSuccessOrderTableClearValidator : OrderTableClearValidator {
    override fun validate(orderTable: OrderTable) {}
}
