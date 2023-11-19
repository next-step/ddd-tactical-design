package kitchenpos.order.tobe.eatinorder.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.util.Map;
import kitchenpos.Fixtures;
import kitchenpos.order.tobe.eatinorder.application.dto.EatInOrderLineItemDto;
import kitchenpos.order.tobe.eatinorder.domain.service.MenuDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EatInOrderLineItemTest {

    @DisplayName("매장 주문 항목 객체를 생성한다")
    @Test
    void testInit() {
        // given
        var menuDto = MenuDto.from(Fixtures.menu());
        var menus = Map.of(menuDto.getId(), menuDto);
        var dto = new EatInOrderLineItemDto(menuDto.getId(), menuDto.getPrice(), 1);

        // when // then
        assertDoesNotThrow(() -> EatInOrderLineItem.from(dto, menus));
    }

    @DisplayName("존재하지 않는 메뉴로는 매장 주문 항목 객체를 생성할 수 없다")
    @Test
    void testInitIfNotExistMenu() {
        // given
        var menu = MenuDto.from(Fixtures.menu());
        var dto = new EatInOrderLineItemDto(menu.getId(), menu.getPrice(), 1);

        var otherMenu = MenuDto.from(Fixtures.menu());
        var menus = Map.of(otherMenu.getId(), otherMenu);

        // when // then
        assertThatThrownBy(() -> EatInOrderLineItem.from(dto, menus))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("숨김 메뉴로는 매장 주문 항목 객체를 생성한다")
    @Test
    void testInitIfMenuIsHide() {
        // given
        var menu = MenuDto.from(Fixtures.menu(10_000L, false, Fixtures.menuProduct(10_000L, 1)));
        var menus = Map.of(menu.getId(), menu);
        var dto = new EatInOrderLineItemDto(menu.getId(), menu.getPrice(), 1);

        // when // then
        assertThatThrownBy(() -> EatInOrderLineItem.from(dto, menus))
            .isExactlyInstanceOf(IllegalStateException.class);
    }

    @DisplayName("가격이 메뉴 가격과 일치하지 않으면 매장 주문 항목 객체를 생성한다")
    @Test
    void testInitIfNotEqualToMenuPrice() {
        // given
        var menu = MenuDto.from(Fixtures.menu());
        var menus = Map.of(menu.getId(), menu);
        var dto = new EatInOrderLineItemDto(menu.getId(), menu.getPrice().add(BigDecimal.ONE), 1);

        // when // then
        assertThatThrownBy(() -> EatInOrderLineItem.from(dto, menus))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
