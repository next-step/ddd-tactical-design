package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.commons.tobe.domain.service.Validator;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Accepted;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Completed;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Served;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Waiting;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static kitchenpos.eatinorders.tobe.domain.fixture.OrderMenuFixture.ORDER_MENU_WITH_MENU_ID_AND_PRICE;
import static kitchenpos.eatinorders.tobe.domain.fixture.OrderLineItemFixture.DEFAULT_ORDER_LINE_ITEM;
import static org.assertj.core.api.Assertions.*;
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
                () -> assertThat(order.getOrderTableId()).isNotNull(),
                () -> assertThat(order.getStatus()).isEqualTo(orderStatus.getStatus()),
                () -> assertThat(order.getOrderDateTime()).isBefore(LocalDateTime.now())
        );
    }

    @DisplayName("정적 생성자 메소드로 주문을 생성한다.")
    @Test
    void 정적_생성자_메소드로_객체_생성_성공() {
        final OrderLineItem orderLineItem = DEFAULT_ORDER_LINE_ITEM();
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));

        final Order order = Order.create(UUID.randomUUID(), UUID.randomUUID(), orderLineItems, dummyValidator);

        assertThat(order.getStatus()).isEqualTo("Waiting");
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void 접수_진행_성공() {
        final OrderLineItem orderLineItem = DEFAULT_ORDER_LINE_ITEM();
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), new Waiting(), orderLineItems, dummyValidator);

        order.accept();

        assertThat(order.getStatus()).isEqualTo(new Accepted().getStatus());
    }

    @DisplayName("주문 접수 시 주문 상태가 접수 대기가 아니면 IllegalStateException을 던진다.")
    @Test
    void 접수_진행_실패() {
        final OrderLineItem orderLineItem = DEFAULT_ORDER_LINE_ITEM();
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), new Accepted(), orderLineItems, dummyValidator);

        ThrowableAssert.ThrowingCallable when = order::accept;

        assertThatIllegalStateException().isThrownBy(when)
                .withMessage("주문이 접수 대기 상태가 아닙니다.");
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void 서빙_진행_성공() {
        final OrderLineItem orderLineItem = DEFAULT_ORDER_LINE_ITEM();
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), new Accepted(), orderLineItems, dummyValidator);

        order.serve();

        assertThat(order.getStatus()).isEqualTo(new Served().getStatus());
    }

    @DisplayName("주문 서빙 시 주문 상태가 접수가 아니면 IllegalStateException을 던진다.")
    @Test
    void 서빙_진행_실패() {
        final OrderLineItem orderLineItem = DEFAULT_ORDER_LINE_ITEM();
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), new Served(), orderLineItems, dummyValidator);

        ThrowableAssert.ThrowingCallable when = order::serve;

        assertThatIllegalStateException().isThrownBy(when)
                .withMessage("주문이 접수 상태가 아닙니다.");
    }

    @DisplayName("주문을 계산 완료한다.")
    @Test
    void 계산_완료_진행_성공() {
        final OrderLineItem orderLineItem = DEFAULT_ORDER_LINE_ITEM();
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), new Served(), orderLineItems, dummyValidator);

        order.complete(dummy -> {
        });

        assertThat(order.getStatus()).isEqualTo(new Completed().getStatus());
    }

    @DisplayName("주문 계산 완료 시 주문 상태가 서빙이 아니면 IllegalStateException을 던진다.")
    @Test
    void 계산_완료_진행_실패() {
        final OrderLineItem orderLineItem = DEFAULT_ORDER_LINE_ITEM();
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), new Completed(), orderLineItems, dummyValidator);

        ThrowableAssert.ThrowingCallable when = () -> order.complete(dummy -> {
        });

        assertThatIllegalStateException().isThrownBy(when)
                .withMessage("주문이 서빙 상태가 아닙니다.");
    }

    @DisplayName("주문 계산 완료 시 주문 항목이 계산 가능하지 않으면 IllegalStateException을 던진다.")
    @Test
    void 계산_완료_시_주문_항목_계산_실패() {
        final OrderLineItem orderLineItem = new OrderLineItem(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new Price(BigDecimal.valueOf(16_000L)),
                -1L
        );
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), new Served(), orderLineItems, dummyValidator);

        ThrowableAssert.ThrowingCallable when = () -> order.complete(dummy -> {
        });

        assertThatIllegalStateException().isThrownBy(when)
                .withMessage("계산 가능하지 않은 주문 항목이 있습니다.");
    }

    @DisplayName("주문은 메뉴 식별자 목록을 반환한다.")
    @Test
    void 메뉴_식별자_목록_반환_성공() {
        final OrderLineItem orderLineItem1 = DEFAULT_ORDER_LINE_ITEM();
        final OrderLineItem orderLineItem2 = DEFAULT_ORDER_LINE_ITEM();
        final OrderLineItems orderLineItems = new OrderLineItems(Arrays.asList(orderLineItem1, orderLineItem2));
        final Order order = Order.create(UUID.randomUUID(), UUID.randomUUID(), orderLineItems, dummyValidator);

        assertThat(order.getMenuIds()).contains(orderLineItem1.getMenuId(), orderLineItem2.getMenuId());
    }

    @DisplayName("주문은 주문 항목 목록 내 주문 가격과 메뉴 가격이 같지 않은 주문 항목이 있으면, IllegalArgumentException을 던진다.")
    @Test
    void validateOrderPrice() {
        final UUID menuId = UUID.randomUUID();
        final OrderMenu orderMenu = ORDER_MENU_WITH_MENU_ID_AND_PRICE(menuId, new Price(BigDecimal.valueOf(16_000L)));
        final OrderLineItem orderLineItem = new OrderLineItem(
                UUID.randomUUID(),
                menuId,
                new Price(BigDecimal.valueOf(20_000L)),
                1L
        );
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = Order.create(UUID.randomUUID(), UUID.randomUUID(), orderLineItems, dummyValidator);
        final OrderMenus orderMenus = new OrderMenus(Collections.singletonList(orderMenu));

        ThrowableAssert.ThrowingCallable when = () -> order.validateOrderPrice(orderMenus);

        assertThatThrownBy(when).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 항목의 가격과 메뉴 가격이 일치하지 않습니다.");
    }
}
