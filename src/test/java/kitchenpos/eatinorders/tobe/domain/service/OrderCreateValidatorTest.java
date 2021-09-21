package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.commons.tobe.domain.model.DisplayedName;
import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.commons.tobe.domain.model.Quantity;
import kitchenpos.commons.tobe.domain.service.Validator;
import kitchenpos.eatinorders.tobe.domain.model.*;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Waiting;
import kitchenpos.eatinorders.tobe.domain.repository.InMemoryMenuRepository;
import kitchenpos.eatinorders.tobe.domain.repository.InMemoryOrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.model.MenuProduct;
import kitchenpos.menus.tobe.domain.model.MenuProducts;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.UUID;

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
        final OrderLineItem orderLineItem = new OrderLineItem(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new Price(BigDecimal.valueOf(16_000L)),
                1L
        );
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = new Order(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new Waiting(),
                orderLineItems,
                dummyOrderCreateValidator
        );

        ThrowableAssert.ThrowingCallable when = () -> orderCreateValidator.validate(order);

        assertThatThrownBy(when).isInstanceOf(NoSuchElementException.class)
                .hasMessage("등록된 주문 테이블이 없습니다.");
    }

    @DisplayName("주문 생성 시, 주문 테이블의 식별자가 빈 테이블의 것이면 IllegalStateException을 던진다.")
    @Test
    void 빈_테이블_실패() {
        final OrderTable orderTable = orderTableRepository.save(DEFAULT_ORDER_TABLE());

        final OrderLineItem orderLineItem = new OrderLineItem(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new Price(BigDecimal.valueOf(16_000L)),
                1L
        );
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = new Order(
                UUID.randomUUID(),
                orderTable.getId(),
                new Waiting(),
                orderLineItems,
                dummyOrderCreateValidator
        );

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

        final OrderLineItem orderLineItem = new OrderLineItem(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new Price(BigDecimal.valueOf(16_000L)),
                1L
        );
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = new Order(
                UUID.randomUUID(),
                orderTable.getId(),
                new Waiting(),
                orderLineItems,
                dummyOrderCreateValidator
        );

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
        final OrderTable orderTable = orderTableRepository.save(DEFAULT_ORDER_TABLE());
        final Menu menu = menuRepository.save(MENU_WITH_PRICE(new Price(BigDecimal.valueOf(16_000L))));

        final OrderLineItem orderLineItem = new OrderLineItem(
                UUID.randomUUID(),
                menu.getId(),
                new Price(BigDecimal.valueOf(16_000L)),
                1L
        );
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = new Order(
                UUID.randomUUID(),
                orderTable.getId(),
                new Waiting(),
                orderLineItems,
                dummyOrderCreateValidator
        );

        ThrowableAssert.ThrowingCallable when = () -> {
            orderTable.sit();
            menu.hide();
            orderCreateValidator.validate(order);
        };

        assertThatThrownBy(when).isInstanceOf(IllegalStateException.class)
                .hasMessage("노출되지 않은 메뉴는 주문할 수 없습니다.");
    }

    @DisplayName("주문 생성 시, 주문 항목의 가격과 메뉴 가격이 일치하지 않으면 IllegalArgumentException을 던진다.")
    @Test
    void 가격_불일치_실패() {
        final OrderTable orderTable = orderTableRepository.save(DEFAULT_ORDER_TABLE());
        final Menu menu = menuRepository.save(MENU_WITH_PRICE(new Price(BigDecimal.valueOf(16_000L))));

        final OrderLineItem orderLineItem = new OrderLineItem(
                UUID.randomUUID(),
                menu.getId(),
                new Price(BigDecimal.valueOf(20_000L)),
                1L
        );
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = new Order(
                UUID.randomUUID(),
                orderTable.getId(),
                new Waiting(),
                orderLineItems,
                dummyOrderCreateValidator
        );

        ThrowableAssert.ThrowingCallable when = () -> {
            orderTable.sit();
            menu.display();
            orderCreateValidator.validate(order);
        };

        assertThatThrownBy(when).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 항목의 가격과 메뉴 가격이 일치하지 않습니다.");
    }

    private static Menu MENU_WITH_PRICE(final Price price) {
        final DisplayedName displayedName = new DisplayedName("후라이드치킨");
        final MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), UUID.randomUUID(), price, new Quantity(1L));
        final MenuProducts menuProducts = new MenuProducts(Collections.singletonList(menuProduct));

        return new Menu(
                UUID.randomUUID(),
                displayedName,
                price,
                menuProducts,
                UUID.randomUUID(),
                true,
                menu -> {
                }
        );
    }

    private static OrderTable DEFAULT_ORDER_TABLE() {
        return new OrderTable(UUID.randomUUID(), new DisplayedName("1번 테이블"), new NumberOfGuests(0L));
    }
}
