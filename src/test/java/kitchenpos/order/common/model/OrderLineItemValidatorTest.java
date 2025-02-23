package kitchenpos.order.common.model;

import static kitchenpos.TestFixtureFactory.createMenu;
import static kitchenpos.TestFixtureFactory.createMenuGroup;
import static kitchenpos.TestFixtureFactory.createProduct;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.menu.infra.persistence.FakeMenuRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderLineItemValidatorTest {

    @Test
    @DisplayName("주문 내역의 메뉴의 id 정보가 올바르지 않으면 예외를 던진다.")
    void validate_another_id_exception() {
        // given
        MenuRepository menuRepository = new FakeMenuRepository(new HashMap<>());
        OrderLineItemValidator orderLineItemValidator = new OrderLineItemValidator(menuRepository);

        Menu firstMenu = createMenu(createMenuGroup(), createProduct(BigDecimal.valueOf(2000)));
        Menu secondMenu = createMenu(createMenuGroup(), createProduct(BigDecimal.valueOf(3000)));
        menuRepository.save(firstMenu);
        menuRepository.save(secondMenu);

        OrderLineItem orderLineItem1 = new OrderLineItem(secondMenu, 2, secondMenu.getId(), BigDecimal.valueOf(8000));
        OrderLineItem orderLineItem2 = new OrderLineItem(secondMenu, 3, secondMenu.getId(), BigDecimal.valueOf(8000));

        // when // then
        Assertions.assertThatThrownBy(() -> orderLineItemValidator.validate(List.of(orderLineItem1, orderLineItem2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 내역의 메뉴 정보가 올바르지 않습니다.");
    }

    @Test
    @DisplayName("주문 내역의 중복 메뉴 존재 시 예외를 던진다.")
    void validate_same_menu_exception() {
        // given
        MenuRepository menuRepository = new FakeMenuRepository(new HashMap<>());
        OrderLineItemValidator orderLineItemValidator = new OrderLineItemValidator(menuRepository);

        Menu firstMenu = createMenu(createMenuGroup(), createProduct(BigDecimal.valueOf(2000)));
        Menu secondMenu = createMenu(createMenuGroup(), createProduct(BigDecimal.valueOf(3000)));
        menuRepository.save(firstMenu);
        menuRepository.save(secondMenu);

        OrderLineItem orderLineItem1 = new OrderLineItem(firstMenu, 2, secondMenu.getId(), BigDecimal.valueOf(8000));
        OrderLineItem orderLineItem2 = new OrderLineItem(secondMenu, 3, secondMenu.getId(), BigDecimal.valueOf(8000));

        // when // then
        Assertions.assertThatThrownBy(() -> orderLineItemValidator.validate(List.of(orderLineItem1, orderLineItem2)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 내역의 메뉴 정보가 올바르지 않습니다.");
    }
}
