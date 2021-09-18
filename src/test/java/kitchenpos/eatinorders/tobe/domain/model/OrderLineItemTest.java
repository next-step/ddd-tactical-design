package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderLineItemTest {

    @DisplayName("주문 항목을 생성한다(주문 항목의 수량은 0보다 적을 수 있다).")
    @ParameterizedTest
    @ValueSource(longs = {-1, 0, 1})
    void 생성_성공(final long quantity) {
        final Price price = new Price(BigDecimal.valueOf(16_000L));

        final OrderLineItem orderLineItem = new OrderLineItem(UUID.randomUUID(), UUID.randomUUID(), price, quantity);

        assertAll(
                () -> assertThat(orderLineItem.getId()).isNotNull(),
                () -> assertThat(orderLineItem.getMenuId()).isNotNull(),
                () -> assertThat(orderLineItem.getPrice()).isEqualTo(price.value()),
                () -> assertThat(orderLineItem.getQuantity()).isEqualTo(quantity)
        );
    }

    @DisplayName("주문 항목은 금액(가격 * 수량)을 반환한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, 0, 1})
    void 금액_반환_성공(final long quantity) {
        final Price price = new Price(BigDecimal.valueOf(16_000L));

        final OrderLineItem orderLineItem = new OrderLineItem(UUID.randomUUID(), UUID.randomUUID(), price, quantity);

        assertThat(orderLineItem.getAmount()).isEqualTo(price.value().multiply(BigDecimal.valueOf(quantity)));
    }
}
