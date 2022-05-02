package kitchenpos.eatinorders.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.OrderLineItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrderLineItemTest {

    @DisplayName("개수가 0 이하라면 예외.")
    @ParameterizedTest
    @ValueSource(longs = {-100, -1})
    void quantity_under_zero(final Long quantity) {
        assertThatThrownBy(() -> new OrderLineItem(quantity, UUID.randomUUID(), BigDecimal.TEN))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격이 0 이하라면 예외.")
    @ParameterizedTest
    @ValueSource(longs = {-100, -1})
    void price_under_zero(final Long price) {
        assertThatThrownBy(() -> new OrderLineItem(3L, UUID.randomUUID(), BigDecimal.valueOf(price)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("생성 성공")
    @Test
    void create() {
        assertDoesNotThrow(() -> new OrderLineItem(3L, UUID.randomUUID(), BigDecimal.TEN));
    }

    @DisplayName("메뉴 가격과 수량을 통해 총 금액 계산.")
    @Test
    void calc_total_price() {
        OrderLineItem sut = new OrderLineItem(3L, UUID.randomUUID(), BigDecimal.TEN);

        assertThat(sut.price()).isEqualTo(BigDecimal.valueOf(30));
    }

}
