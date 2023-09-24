package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.TobeFixtures;
import kitchenpos.eatinorders.domain.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TobeOrderTest {

    @DisplayName("주문을 생성한다.")
    @Test
    void create() {
        TobeOrder tobeOrder = new TobeOrder(UUID.randomUUID(), OrderStatus.WAITING, LocalDateTime.now(),
                                            UUID.randomUUID(),
                                            new TobeOrderLineItems(List.of(TobeFixtures.orderLineItem())));
        assertThat(tobeOrder.getId()).isNotNull();
    }

    @DisplayName("주문된 음식은 없을 수 없다.")
    @Test
    void create01() {
        assertThatThrownBy(() -> new TobeOrder(UUID.randomUUID(), OrderStatus.WAITING, LocalDateTime.now(),
                                               UUID.randomUUID(), new TobeOrderLineItems(Collections.emptyList())))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void accept01() {
        TobeOrder tobeOrder = new TobeOrder(UUID.randomUUID(), OrderStatus.WAITING, LocalDateTime.now(),
                                            UUID.randomUUID(),
                                            new TobeOrderLineItems(List.of(TobeFixtures.orderLineItem())));
        tobeOrder.accept();
        assertThat(tobeOrder.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @DisplayName("주문을 접수 시 주문이 준비중일 때만 가능하다.")
    @Test
    void accept02() {
        TobeOrder tobeOrder = new TobeOrder(UUID.randomUUID(), OrderStatus.ACCEPTED, LocalDateTime.now(),
                                            UUID.randomUUID(),
                                            new TobeOrderLineItems(List.of(TobeFixtures.orderLineItem())));
        assertThatThrownBy(() -> tobeOrder.accept())
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void served01() {
        TobeOrder tobeOrder = new TobeOrder(UUID.randomUUID(), OrderStatus.ACCEPTED, LocalDateTime.now(),
                                            UUID.randomUUID(),
                                            new TobeOrderLineItems(List.of(TobeFixtures.orderLineItem())));
        tobeOrder.served();
        assertThat(tobeOrder.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("주문을 서빙 시 주문이 접수일 때만 가능하다.")
    @Test
    void served02() {
        TobeOrder tobeOrder = new TobeOrder(UUID.randomUUID(), OrderStatus.WAITING, LocalDateTime.now(),
                                            UUID.randomUUID(),
                                            new TobeOrderLineItems(List.of(TobeFixtures.orderLineItem())));
        assertThatThrownBy(() -> tobeOrder.served())
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 종료한다.")
    @Test
    void completed01() {
        TobeOrder tobeOrder = new TobeOrder(UUID.randomUUID(), OrderStatus.SERVED, LocalDateTime.now(),
                                            UUID.randomUUID(),
                                            new TobeOrderLineItems(List.of(TobeFixtures.orderLineItem())));
        tobeOrder.completed();
        assertThat(tobeOrder.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @DisplayName("주문을 종료 시 주문이 서빙일 때만 가능하다.")
    @Test
    void completed02() {
        TobeOrder tobeOrder = new TobeOrder(UUID.randomUUID(), OrderStatus.WAITING, LocalDateTime.now(),
                                            UUID.randomUUID(),
                                            new TobeOrderLineItems(List.of(TobeFixtures.orderLineItem())));
        assertThatThrownBy(() -> tobeOrder.completed())
                .isInstanceOf(IllegalStateException.class);
    }
}
