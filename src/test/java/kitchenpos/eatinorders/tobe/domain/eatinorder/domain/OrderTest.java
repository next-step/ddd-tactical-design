package kitchenpos.eatinorders.tobe.domain.eatinorder.domain;

import kitchenpos.eatinorders.model.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Arrays;

import static kitchenpos.eatinorders.TobeFixtures.orderLineItem;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @DisplayName("생성테스트")
    @Test
    public void create() {
        Order result = new Order(1L, Arrays.asList(orderLineItem()));

        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.getOrderTableId()).isEqualTo(1L),
                () -> assertThat(result.getOrderLineItems()).contains(orderLineItem())
        );
    }

    @DisplayName("주문 아이템 업데이트 테스트.")
    @Test
    public void updateOrderLineItems() {
        OrderLineItem sample = new OrderLineItem(40L, 40);
        Order result = new Order(1L, Arrays.asList(orderLineItem()));
        result.updateOrderLineItems(Arrays.asList(sample));

        assertThat(result).isNotNull();
        assertThat(result.getOrderLineItems()).isEqualTo(Arrays.asList(sample));
    }

    @DisplayName("주문 상태 바꾸기 테스트.")
    @ParameterizedTest
    @EnumSource(value = OrderStatus.class, names = {"MEAL", "COOKING"})
    public void changeOrderStatus(final OrderStatus orderStatus) {
        Order result = new Order(1L, Arrays.asList(orderLineItem()));
        result.changeOrderStatus(new Order(orderStatus));

        assertThat(result).isNotNull();
        assertThat(result.getOrderStatus()).isEqualTo(orderStatus);
        assertThat(result.getOrderLineItems()).isEqualTo(Arrays.asList(orderLineItem()));
    }

    @DisplayName("주문 상태가 계산 완료인 경우 변경할 수 없다.")
    @Test
    public void changeOrderStatusFailure() {
        Order result = new Order(1L, Arrays.asList(orderLineItem()));

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> result.changeOrderStatus(new Order(OrderStatus.COMPLETION)))
        ;
    }
}