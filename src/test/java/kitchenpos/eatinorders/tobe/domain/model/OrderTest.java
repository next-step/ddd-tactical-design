package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.commons.tobe.domain.service.Validator;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Accepted;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Served;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Waiting;
import kitchenpos.menus.tobe.domain.model.Menu;
import org.assertj.core.api.ThrowableAssert;
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

import static kitchenpos.eatinorders.tobe.domain.fixture.MenuFixture.MENU_WITH_ID_AND_PRICE;
import static kitchenpos.eatinorders.tobe.domain.fixture.OrderLineItemFixture.DEFAULT_ORDER_LINE_ITEM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderTest {

    private final Validator<Order> dummyValidator = order -> {
    };

    @DisplayName("주문을 생성한다.")
    @Test
    void 생성_성공() {
        final OrderLineItem orderLineItem = DEFAULT_ORDER_LINE_ITEM();
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
        final OrderLineItem orderLineItem = DEFAULT_ORDER_LINE_ITEM();
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), orderStatus, orderLineItems, dummyValidator);

        order.proceed(dummy -> {
        });

        assertThat(order.getStatus()).isEqualTo(orderStatusValue);
    }

    @DisplayName("주문은 메뉴 식별자 목록을 반환한다.")
    @Test
    void 메뉴_식별자_목록_반환_성공() {
        final OrderLineItem orderLineItem1 = DEFAULT_ORDER_LINE_ITEM();
        final OrderLineItem orderLineItem2 = DEFAULT_ORDER_LINE_ITEM();
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

    @DisplayName("주문은 주문 항목 목록 내 주문 가격과 메뉴 가격이 같지 않은 주문 항목이 있으면, IllegalArgumentException을 던진다.")
    @Test
    void validateOrderPrice() {
        final UUID menuId = UUID.randomUUID();
        final Menu menu = MENU_WITH_ID_AND_PRICE(menuId, new Price(BigDecimal.valueOf(16_000L)));
        final OrderLineItem orderLineItem = new OrderLineItem(
                UUID.randomUUID(),
                menuId,
                new Price(BigDecimal.valueOf(20_000L)),
                1L
        );
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), new Waiting(), orderLineItems, dummyValidator);

        ThrowableAssert.ThrowingCallable when = () -> order.validateOrderPrice(Collections.singletonList(menu));

        assertThatThrownBy(when).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 항목의 가격과 메뉴 가격이 일치하지 않습니다.");
    }
}
