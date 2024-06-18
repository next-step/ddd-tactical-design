package kitchenpos.eatinorders.tobe.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

/**
 * @param orderLineItemRequests 주문한 메뉴
 */
public record EatInOrderCreateRequest(
        @NotNull(message = "주문한 메뉴 목록은 필수 값입니다.")
        List<OrderLineItemRequest> orderLineItemRequests,
        @NotNull(message = "주문 테이블 식별자는  필수 값입니다.")
        UUID orderTableId
) {
    public record OrderLineItemRequest(
            @NotNull(message = "메뉴 식별자는 필수 값입니다.")
            UUID menuId,
            @NotNull(message = "메뉴 수량은 필수 값입니다.")
            long quantity
    ) {
    }
}
