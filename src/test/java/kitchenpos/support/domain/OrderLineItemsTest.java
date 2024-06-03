package kitchenpos.support.domain;

import kitchenpos.eatinorders.exception.KitchenPosIllegalArgumentException;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.support.infra.MenuClientImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.INVALID_ID;
import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("주문아이템 목록")
class OrderLineItemsTest {
    private MenuRepository menuRepository;
    private MenuClient menuClient;
    private OrderLineItem orderLineItem;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        menuClient = new MenuClientImpl(menuRepository);
        UUID menuId = menuRepository.save(menu(19_000, true, menuProduct())).getId();
        orderLineItem = new OrderLineItem(menuId, BigDecimal.valueOf(19_000), 2L);
    }

    @DisplayName("[성공] 주문아이템 목록이 생성된다.")
    @Test
    void create() {
        OrderLineItems actual = assertDoesNotThrow(() -> OrderLineItems.of(List.of(orderLineItem), menuClient));
        assertThat(actual).isEqualTo(OrderLineItems.of(List.of(orderLineItem), menuClient));
    }

    @DisplayName("[실패] 주문아이템 목록에 구성할 주문아이템이 없으면 생성할 수 없다.")
    @MethodSource("orderLineItems")
    @ParameterizedTest
    void fail_create(final List<OrderLineItem> orderLineItems) {
        assertThatThrownBy(() -> OrderLineItems.of(orderLineItems, menuClient))
                .isInstanceOf(KitchenPosIllegalArgumentException.class);
    }

    @DisplayName("[실패] 주문아이템 목록에 미리 등록된 메뉴가 중복으로 구성되지 않아야 한다.")
    @Test
    void fail2_create() {
        OrderLineItem invalidOrderLineItem = new OrderLineItem(INVALID_ID, BigDecimal.valueOf(19_000), 1L);
        List<OrderLineItem> orderLineItems = List.of(invalidOrderLineItem);
        assertThatThrownBy(() -> OrderLineItems.of(orderLineItems, menuClient))
                .isInstanceOf(KitchenPosIllegalArgumentException.class);
    }

    private static List<Arguments> orderLineItems() {
        return Arrays.asList(
                null,
                Arguments.of(Collections.emptyList())
        );
    }
}
