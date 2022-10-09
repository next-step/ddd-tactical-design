package kitchenpos.eatinorders.order.tobe.domain;

import kitchenpos.common.domain.vo.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EatInOrderLineItemTest {

    @DisplayName("주문항목을 생성한다.")
    @Test
    void create() {
        final EatInOrderLineItem eatInOrderLineItem = EatInOrderLineItem.create(UUID.randomUUID(), 15_000L, 2);

        assertAll(
                () -> assertThat(eatInOrderLineItem.menuId()).isNotNull(),
                () -> assertThat(eatInOrderLineItem.price()).isEqualTo(Price.valueOf(15_000L)),
                () -> assertThat(eatInOrderLineItem.quantity()).isEqualTo(2)
        );
    }
}
