package kitchenpos.support.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static kitchenpos.fixture.MenuFixture.menu;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("주문아이템")
class OrderLineItemTest {

    @DisplayName("[성공] 주문아이템을 생성한다.")
    @Test
    void create() {
        UUID menuId = menu().getId();
        BigDecimal price = BigDecimal.valueOf(34_000);
        long quantity = 1L;

        OrderLineItem orderLineItem = new OrderLineItem(menuId, price, quantity);

        assertAll(
                () -> assertThat(orderLineItem.getMenuId()).isEqualTo(menuId),
                () -> assertThat(orderLineItem.getPrice()).isEqualTo(BigDecimal.valueOf(34_000)),
                () -> assertThat(orderLineItem.getQuantity()).isEqualTo(1L)
        );
    }
}
