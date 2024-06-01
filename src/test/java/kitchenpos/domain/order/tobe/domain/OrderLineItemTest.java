package kitchenpos.domain.order.tobe.domain;

import kitchenpos.domain.support.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class OrderLineItemTest {

    @DisplayName("주문 항목을 생성한다")
    @Test
    void constructor() {
        OrderLineItem orderLineItem = new OrderLineItem(UUID.randomUUID(), 0, new Price(BigDecimal.ZERO));
        assertThat(orderLineItem.quantity()).isZero();
        assertThat(orderLineItem.price()).isEqualTo(BigDecimal.ZERO);
    }

    @DisplayName("주문 항목의 수량이 음수인 주문 항목을 생성한다")
    @Test
    void constructor_minus_quantity() {
        OrderLineItem orderLineItem = new OrderLineItem(UUID.randomUUID(), -1, new Price(BigDecimal.ZERO));
        assertThat(orderLineItem.quantity()).isEqualTo(-1);
        assertThat(orderLineItem.price()).isEqualTo(BigDecimal.ZERO);
    }

    @DisplayName("주문 항목의 가격이 음수이면 생성을 실패한다")
    @Test
    void constructor_price_minus_fail() {
        assertThatIllegalArgumentException().isThrownBy(() -> new OrderLineItem(UUID.randomUUID(), 0, new Price(BigDecimal.valueOf(-1))));
    }

    @DisplayName("주문 항목의 가격이 Null이면 생성을 실패한다")
    @Test
    void constructor_price_null_fail() {
        assertThatIllegalArgumentException().isThrownBy(() -> new OrderLineItem(UUID.randomUUID(), 0, new Price(null)));
    }
}
