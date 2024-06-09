package kitchenpos.support.domain;

import kitchenpos.support.dto.OrderLineItemCreateRequest;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.fixture.MenuFixture.menu;
import static kitchenpos.fixture.MenuFixture.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("주문아이템 목록")
class OrderLineItemsTest {
    private OrderLineItemCreateRequest orderLineItem;

    @BeforeEach
    void setUp() {
        MenuRepository menuRepository = new InMemoryMenuRepository();
        UUID menuId = menuRepository.save(menu(19_000, true, menuProduct())).getId();
        orderLineItem = new OrderLineItemCreateRequest(menuId, BigDecimal.valueOf(19_000), 2L);
    }

    @DisplayName("[성공] 주문아이템 목록이 생성된다.")
    @Test
    void create() {
        OrderLineItems actual = assertDoesNotThrow(() -> OrderLineItems.from(List.of(orderLineItem)));
        assertThat(actual).isEqualTo(OrderLineItems.from(List.of(orderLineItem)));
    }
}
