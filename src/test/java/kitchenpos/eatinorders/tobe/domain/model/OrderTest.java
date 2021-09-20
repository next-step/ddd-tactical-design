package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.commons.tobe.domain.service.Validator;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Accepted;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Served;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Waiting;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderTest {

    private final Validator<Order> dummyValidator = order -> {
    };

    @DisplayName("주문을 생성한다.")
    @Test
    void 생성_성공() {
        final OrderLineItem orderLineItem = new OrderLineItem(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new Price(BigDecimal.valueOf(20_000L)),
                1L
        );
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final OrderStatus orderStatus = new Waiting();

        final Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), orderStatus, orderLineItems, dummyValidator);

        assertAll(
                () -> assertThat(order.getId()).isNotNull(),
                () -> assertThat(order.getTableId()).isNotNull(),
                () -> assertThat(order.getStatus()).isEqualTo(orderStatus.getStatus()),
                () -> assertThat(order.getOrderDateTime()).isBefore(LocalDateTime.now())
        );
    }

    @DisplayName("주문은 다음 상태로 진행된다.")
    @ParameterizedTest
    @MethodSource("provideArguments")
    void 상태_진행_성공(final OrderStatus orderStatus, final String orderStatusValue) {
        final OrderLineItem orderLineItem = new OrderLineItem(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new Price(BigDecimal.valueOf(20_000L)),
                1L
        );
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), orderStatus, orderLineItems, dummyValidator);

        order.proceed();

        assertThat(order.getStatus()).isEqualTo(orderStatusValue);
    }

    @DisplayName("주문은 메뉴 식별자 목록을 반환한다.")
    @Test
    void 메뉴_식별자_목록_반환_성공() {
        final OrderLineItem orderLineItem1 = new OrderLineItem(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new Price(BigDecimal.valueOf(20_000L)),
                1L
        );
        final OrderLineItem orderLineItem2 = new OrderLineItem(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new Price(BigDecimal.valueOf(20_000L)),
                1L
        );
        final OrderLineItems orderLineItems = new OrderLineItems(Arrays.asList(orderLineItem1, orderLineItem2));
        final Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), new Waiting(), orderLineItems, dummyValidator);

        assertThat(order.getMenuIds()).contains(orderLineItem1.getMenuId(), orderLineItem2.getMenuId());
    }

    private static Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(new Waiting(), "Accepted"),
                Arguments.of(new Accepted(), "Served"),
                Arguments.of(new Served(), "Completed")
        );
    }
}
