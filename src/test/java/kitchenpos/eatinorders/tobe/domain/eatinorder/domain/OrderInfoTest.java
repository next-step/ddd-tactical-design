package kitchenpos.eatinorders.tobe.domain.eatinorder.domain;

import kitchenpos.eatinorders.model.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class OrderInfoTest {

    @DisplayName("생성테스트")
    @Test
    void create() {
        OrderInfo result = OrderInfo.createCooking(1L);

        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.getOrderTableId()).isEqualTo(1L),
                () -> assertThat(result.getOrderStatus()).isEqualTo(OrderStatus.COOKING)
        );
    }

    @DisplayName("주문 정보 상태 바꾸기 테스트.")
    @ParameterizedTest
    @EnumSource(value = OrderStatus.class, names = {"MEAL", "COOKING"})
    void changeOrderStatus(final OrderStatus orderStatus) {
        OrderInfo result = new OrderInfo(orderStatus);

        result.changeOrderStatus();

        assertThat(result).isNotNull();
        assertAll(
                () -> assertThat(result.getOrderStatus()).isEqualTo(orderStatus)
        );
    }

    @DisplayName("주문이 완료상태이면 바꿀 수 없음.")
    @Test
    void changeOrderStatusFailure() {
        OrderInfo result = new OrderInfo(OrderStatus.COMPLETION);

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() ->  result.changeOrderStatus())
        ;
    }
}