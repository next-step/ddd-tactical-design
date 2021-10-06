package kitchenpos.eatinorders.tobe.eatinorder.domain;

import kitchenpos.eatinorders.tobe.ordertable.domain.OrderTable;
import kitchenpos.fixture.EatInOrderFixture;
import kitchenpos.fixture.OrderTableFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("매장 주문(EatInOrder)은")
class EatInOrderTest {
    private static final List<OrderLineItem> ORDER_LINE_ITEMS = Arrays.asList(EatInOrderFixture.주문상품(), EatInOrderFixture.주문상품());
    private static final OrderTable ORDER_TABLE = OrderTableFixture.앉은테이블();

    @DisplayName("생성할 수 있다.")
    @Test
    void create() {
        final EatInOrder order = new EatInOrder(ORDER_LINE_ITEMS, ORDER_TABLE);
        assertThat(order.getId()).isNotNull();
        assertThat(order).isEqualTo(new EatInOrder(ORDER_LINE_ITEMS, ORDER_TABLE));
    }
}
