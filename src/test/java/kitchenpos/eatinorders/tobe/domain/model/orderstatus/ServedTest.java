package kitchenpos.eatinorders.tobe.domain.model.orderstatus;

import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.eatinorders.tobe.domain.model.Order;
import kitchenpos.eatinorders.tobe.domain.model.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.model.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.model.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ServedTest {

    @DisplayName("서빙 상태를 생성한다.")
    @Test
    void 생성_성공() {
        final OrderStatus orderStatus = new Served();

        assertThat(orderStatus.getStatus()).isEqualTo("Served");
    }

    @DisplayName("서빙은 계산 완료로 진행된다.")
    @Test
    void 상태_진행_성공() {
        final OrderLineItem orderLineItem = new OrderLineItem(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new Price(BigDecimal.valueOf(16_000L)),
                1L
        );
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), orderLineItems);
        final OrderStatus orderStatus = new Served();

        assertThat(orderStatus.proceed(order)).isInstanceOf(Completed.class);
    }
}
