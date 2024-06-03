package kitchenpos.eatinorders.tobe.infra

import kitchenpos.eatinorders.tobe.dto.MenuValidateRequest

interface CheckMenuBeforeOrder {
    fun canOrder(
        menuValidateRequests: List<MenuValidateRequest>,
    ): Boolean
}
