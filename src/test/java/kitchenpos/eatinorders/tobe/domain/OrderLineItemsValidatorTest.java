package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.common.vo.Price;
import kitchenpos.eatinorders.tobe.domain.common.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.common.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderLineItemsException;
import kitchenpos.menus.infra.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.products.tobe.domain.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderLineItemsValidatorTest {

    private MenuRepository menuRepository;
    private OrderLineItemsValidator orderLineItemsValidator;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        orderLineItemsValidator = new OrderLineItemsValidator(menuRepository);
    }

    @DisplayName("주문 항목에 존재하지 않는 메뉴가 포함되면 예외 발생한다")
    @Test
    void nonExistMenu() {
        MenuId nonExistMenuId = MenuId.generate();
        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, nonExistMenuId, 1, new Price(10_000))
        );

        assertThatThrownBy(() -> orderLineItemsValidator.validate(orderLineItems))
                .isInstanceOf(InvalidOrderLineItemsException.class);
    }

    @DisplayName("주문 항목에 비노출 메뉴가 포함되면 예외가 발생한다")
    @ValueSource(booleans = {false})
    @ParameterizedTest
    void hiddenMenu(boolean displayed) {
        Menu menu = menuRepository.save(createMenu("후라이드치킨", 25_000, displayed));

        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, menu.getId(), 1, new Price(10_000))
        );

        assertThatThrownBy(() -> orderLineItemsValidator.validate(orderLineItems))
                .isInstanceOf(InvalidOrderLineItemsException.class);
    }

    @DisplayName("주문 항목의 메뉴 가격이 실제 메뉴 가격과 다르면 예외가 발생한다")
    @ValueSource(ints = {26_000})
    @ParameterizedTest
    void hasDifferentPrice(int differentPrice) {
        Menu menu = menuRepository.save(createMenu("후라이드치킨", 25_000, true));

        OrderLineItems orderLineItems = new OrderLineItems(
                new OrderLineItem(1L, menu.getId(), 1, new Price(differentPrice))
        );

        assertThatThrownBy(() -> orderLineItemsValidator.validate(orderLineItems))
                .isInstanceOf(InvalidOrderLineItemsException.class);
    }

    private Menu createMenu(String name, int price, boolean displayed) {
        return new Menu(
                MenuId.generate(),
                new MenuName(name, (menuName) -> false),
                new Price(price),
                MenuGroupId.generate(),
                new MenuProducts(
                        new MenuProduct(ProductId.generate(), 1, price)
                ),
                displayed
        );
    }
}
