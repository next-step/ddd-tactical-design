package kitchenpos.eatinorders.tobe.domain.eatinorder.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderLineItemTest {

    @DisplayName("생성 테스트.")
    @Test
    public void create() {
        OrderLineItem result = new OrderLineItem(1L, 3);

        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.getMenuId()).isEqualTo(1L),
                () -> assertThat(result.getQuantity()).isEqualTo(3)
        );
    }

}