package kitchenpos.eatinorders.tobe.infra

import kitchenpos.eatinorders.tobe.dto.MenuValidateRequest
import org.springframework.stereotype.Component

@Component
class DefaultCheckMenuBeforeOrder: CheckMenuBeforeOrder {
    // 하위 검증
    // 1. 메뉴 아이디로 메뉴 List 가져와 사이즈 비교
    // 2. 메뉴 전시 상태 여부 검증
    // 3. 메뉴 가격이랑 주문 가격이랑 비교
    override fun canOrder(menuValidateRequests: List<MenuValidateRequest>): Boolean {
        return true
    }
}
