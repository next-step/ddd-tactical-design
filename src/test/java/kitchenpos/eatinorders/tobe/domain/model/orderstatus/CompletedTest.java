package kitchenpos.eatinorders.tobe.domain.model.orderstatus;

import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.eatinorders.tobe.domain.model.Order;
import kitchenpos.eatinorders.tobe.domain.model.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.model.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.model.OrderStatus;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CompletedTest {

    @DisplayName("계산 완료 상태를 생성한다.")
    @Test
    void 생성_성공() {
        final OrderStatus orderStatus = new Completed();

        assertThat(orderStatus.getStatus()).isEqualTo("Completed");
    }

    @DisplayName("계산 완료는 진행하면 IllegalStateException을 던진다.")
    @Test
    void 상태_진행_실패() {
        final OrderLineItem orderLineItem = new OrderLineItem(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new Price(BigDecimal.valueOf(16_000L)),
                1L
        );
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), orderLineItems, dummy -> {
        });
        final OrderStatus orderStatus = new Served();

        ThrowableAssert.ThrowingCallable when = () -> orderStatus.proceed(order);

        assertThatThrownBy(when).isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 계산 완료된 상태입니다.");
    }
}
