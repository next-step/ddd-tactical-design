package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.commons.tobe.domain.service.Validator;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Accepted;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Completed;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Served;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Waiting;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @MethodSource("provideArguments1")
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

    private static Stream<Arguments> provideArguments1() {
        return Stream.of(
                Arguments.of(new Waiting(), "Accepted"),
                Arguments.of(new Accepted(), "Served"),
                Arguments.of(new Served(), "Completed")
        );
    }

    @DisplayName("주문은 계산 완료 여부를 반환한다.")
    @ParameterizedTest
    @MethodSource("provideArguments2")
    void 계산_완료_여부_반환_성공(final OrderStatus orderStatus, final boolean isCompleted) {
        final OrderLineItem orderLineItem = new OrderLineItem(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new Price(BigDecimal.valueOf(20_000L)),
                1L
        );
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), orderStatus, orderLineItems, dummyValidator);

        assertThat(order.isCompleted()).isEqualTo(isCompleted);
    }

    private static Stream<Arguments> provideArguments2() {
        return Stream.of(
                Arguments.of(new Waiting(), false),
                Arguments.of(new Accepted(), false),
                Arguments.of(new Served(), false),
                Arguments.of(new Completed(), true)
        );
    }
}
