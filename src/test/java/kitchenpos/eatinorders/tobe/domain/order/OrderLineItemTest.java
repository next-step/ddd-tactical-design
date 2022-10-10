package kitchenpos.eatinorders.tobe.domain.order;

import kitchenpos.eatinorders.tobe.domain.vo.NumberOfGuests;
import kitchenpos.global.vo.DisplayedName;
import kitchenpos.global.vo.Price;
import kitchenpos.global.vo.Quantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("주문 아이템")
class OrderLineItemTest {

    private UUID menuId;

    @BeforeEach
    void setUp() {
        menuId = UUID.randomUUID();
    }

    @DisplayName("주문 아이템 생성")
    @Test
    void createOrderLineItem() {
        OrderLineItem orderLineItem = new OrderLineItem(menuId, new Quantity(2), new Price(15_000));

        assertAll(
                () -> assertThat(orderLineItem.getQuantity()).isEqualTo(new Quantity(2)),
                () -> assertThat(orderLineItem.getPrice()).isEqualTo(new Price(15_000))
        );
    }

    @DisplayName("주문 아이템 전체 가격")
    @Test
    void orderLineItemTotalPrice() {
        OrderLineItem orderLineItem = new OrderLineItem(menuId, new Quantity(2), new Price(15_000));

        assertThat(orderLineItem.totalPrice()).isEqualTo(BigDecimal.valueOf(30_000));
    }
}
