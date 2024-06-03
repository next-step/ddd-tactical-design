package kitchenpos.eatinorders.tobe.domain

import kitchenpos.eatinorders.tobe.dto.MenuValidateRequest
import kitchenpos.eatinorders.tobe.infra.CheckMenuBeforeOrder
import org.springframework.stereotype.Component

@Component
class MenuValidator(
    private val checkMenuBeforeOrder: CheckMenuBeforeOrder,
) {
    fun canOrder(
        requests: List<MenuValidateRequest>,
    ) {
        check(checkMenuBeforeOrder.canOrder(requests)) { "해당 메뉴는 주문이 불가능합니다. 관리자에게 문의 부탁드립니다." }
    }
}
