package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.commons.tobe.domain.service.Validator;
import kitchenpos.eatinorders.tobe.domain.model.*;
import kitchenpos.eatinorders.tobe.domain.repository.InMemoryMenuRepository;
import kitchenpos.eatinorders.tobe.domain.repository.InMemoryOrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.UUID;

import static kitchenpos.eatinorders.tobe.domain.fixture.MenuFixture.MENU_WITH_PRICE;
import static kitchenpos.eatinorders.tobe.domain.fixture.MenuFixture.NOT_DISPLAYED_MENU;
import static kitchenpos.eatinorders.tobe.domain.fixture.OrderLineItemFixture.*;
import static kitchenpos.eatinorders.tobe.domain.fixture.OrderTableFixture.DEFAULT_ORDER_TABLE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderCreateValidatorTest {

    private OrderTableRepository orderTableRepository;

    private MenuRepository menuRepository;

    private Validator<Order> orderCreateValidator;

    private final Validator<Order> dummyOrderCreateValidator = order -> {
    };

    @BeforeEach
    void setUp() {
        orderTableRepository = new InMemoryOrderTableRepository();
        menuRepository = new InMemoryMenuRepository();
        orderCreateValidator = new OrderCreateValidator(orderTableRepository, menuRepository);
    }

    @DisplayName("주문 생성 시, 등록된 주문 테이블의 식별자가 아니면 NoSuchElementException을 던진다.")
    @Test
    void 주문_테이블_누락_실패() {
        final OrderLineItem orderLineItem = DEFAULT_ORDER_LINE_ITEM();
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = Order.create(UUID.randomUUID(), UUID.randomUUID(), orderLineItems, dummyOrderCreateValidator);

        ThrowableAssert.ThrowingCallable when = () -> orderCreateValidator.validate(order);

        assertThatThrownBy(when).isInstanceOf(NoSuchElementException.class)
                .hasMessage("등록된 주문 테이블이 없습니다.");
    }

    @DisplayName("주문 생성 시, 주문 테이블의 식별자가 빈 테이블의 것이면 IllegalStateException을 던진다.")
    @Test
    void 빈_테이블_실패() {
        final OrderTable orderTable = orderTableRepository.save(DEFAULT_ORDER_TABLE());
        final OrderLineItem orderLineItem = DEFAULT_ORDER_LINE_ITEM();
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = Order.create(UUID.randomUUID(), orderTable.getId(), orderLineItems, dummyOrderCreateValidator);

        ThrowableAssert.ThrowingCallable when = () -> {
            orderTable.clear(dummy -> {
            });
            orderCreateValidator.validate(order);
        };

        assertThatThrownBy(when).isInstanceOf(IllegalStateException.class)
                .hasMessage("빈 테이블에는 주문을 등록할 수 없습니다.");
    }

    @DisplayName("주문 생성 시, 등록된 메뉴의 식별자가 아니면 IllegalArgumentException을 던진다.")
    @Test
    void 메뉴_누락_실패() {
        final OrderTable orderTable = orderTableRepository.save(DEFAULT_ORDER_TABLE());

        final OrderLineItem orderLineItem = DEFAULT_ORDER_LINE_ITEM();
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = Order.create(UUID.randomUUID(), orderTable.getId(), orderLineItems, dummyOrderCreateValidator);

        ThrowableAssert.ThrowingCallable when = () -> {
            orderTable.sit();
            orderCreateValidator.validate(order);
        };

        assertThatThrownBy(when).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("등록된 메뉴가 없습니다.");
    }

    @DisplayName("주문 생성 시, 노출되지 않은 메뉴의 식별자가 포함되면 IllegalStateException을 던진다.")
    @Test
    void 비노출_메뉴_포함_실패() {
        final Menu menu = menuRepository.save(NOT_DISPLAYED_MENU());
        final OrderTable orderTable = orderTableRepository.save(DEFAULT_ORDER_TABLE());
        final OrderLineItem orderLineItem = ORDER_LINE_ITEM_WITH_MENU_ID(menu.getId());
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = Order.create(UUID.randomUUID(), orderTable.getId(), orderLineItems, dummyOrderCreateValidator);

        ThrowableAssert.ThrowingCallable when = () -> {
            orderTable.sit();
            orderCreateValidator.validate(order);
        };

        assertThatThrownBy(when).isInstanceOf(IllegalStateException.class)
                .hasMessage("노출되지 않은 메뉴는 주문할 수 없습니다.");
    }

    @DisplayName("주문 생성 시, 주문 항목의 가격과 메뉴 가격이 일치하지 않으면 IllegalArgumentException을 던진다.")
    @Test
    void 가격_불일치_실패() {
        final Menu menu = menuRepository.save(MENU_WITH_PRICE(new Price(16_000L)));
        final OrderTable orderTable = orderTableRepository.save(DEFAULT_ORDER_TABLE());
        final OrderLineItem orderLineItem = ORDER_LINE_ITEM_WITH_MENU_ID_AND_PRICE(menu.getId(), 20_000L);
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = Order.create(UUID.randomUUID(), orderTable.getId(), orderLineItems, dummyOrderCreateValidator);

        ThrowableAssert.ThrowingCallable when = () -> {
            orderTable.sit();
            orderCreateValidator.validate(order);
        };

        assertThatThrownBy(when).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 항목의 가격과 메뉴 가격이 일치하지 않습니다.");
    }
}
