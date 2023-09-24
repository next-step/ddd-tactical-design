package kitchenpos.takeoutorders.tobe.application.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TakeOutOrderRequestTest {

    @DisplayName("메뉴가 없으면 등록할 수 없다")
    @ParameterizedTest
    @NullAndEmptySource
    void validateOrderLineItems(List<OrderLineItemRequest> orderLineItemList) {
        final TakeOutOrderRequest request = new TakeOutOrderRequest(orderLineItemList);
        assertThatThrownBy(request::validate)
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
