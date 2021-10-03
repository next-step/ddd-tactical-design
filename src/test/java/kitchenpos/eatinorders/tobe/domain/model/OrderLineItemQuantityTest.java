package kitchenpos.eatinorders.tobe.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderLineItemQuantityTest {

    @DisplayName("생성 검증")
    @Test
    void create() {
        Assertions.assertDoesNotThrow(() -> new OrderLineItemQuantity(BigDecimal.valueOf(2L)));
    }

    @DisplayName("null 생성시 에러 검증")
    @Test
    void nullValue() {
        assertThatThrownBy(() -> new OrderLineItemQuantity(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("동등성 검증")
    @Test
    void equals() {
        OrderLineItemQuantity orderLineItemQuantity1 = new OrderLineItemQuantity(BigDecimal.valueOf(2L));
        OrderLineItemQuantity orderLineItemQuantity2 = new OrderLineItemQuantity(BigDecimal.valueOf(2L));
        assertThat(orderLineItemQuantity1.equals(orderLineItemQuantity2))
                .isTrue();
    }

}
