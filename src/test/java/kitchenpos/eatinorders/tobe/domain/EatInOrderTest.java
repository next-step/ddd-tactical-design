package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.domain.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.vo.OrderTableId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EatInOrderTest {

    @DisplayName("주문 항목이 없거나 비어있으면 매장 주문을 생성할 수 없다.")
    @ParameterizedTest(name = "주문 항목: {0}")
    @NullAndEmptySource
    void createWithoutEatInOrderLineItems(final List<EatInOrderLineItem> eatInOrderLineItems) {

        assertThatThrownBy(() -> new EatInOrder(eatInOrderLineItems, new OrderTableId()))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 테이블이 없으면 매장 주문을 생성할 수 없다.")
    @NullSource
    @ParameterizedTest(name = "주문 테이블: {0}")
    void createWithoutOrderTable(final OrderTableId orderTableId) {
        final EatInOrderLineItem eatInOrderLineItem = new EatInOrderLineItem(new EatInOrderLineItemMenu(
                UUID.randomUUID(), "후라이드 치킨", 16_000
        ));

        assertThatThrownBy(() -> new EatInOrder(List.of(eatInOrderLineItem), orderTableId))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
