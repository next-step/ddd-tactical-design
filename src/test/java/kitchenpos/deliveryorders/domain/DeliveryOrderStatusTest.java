package kitchenpos.deliveryorders.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class DeliveryOrderStatusTest {

    @DisplayName("다음 주문상태를 정상 조회 한다.")
    @ParameterizedTest
    @EnumSource(value = DeliveryOrderStatus.class, names = "COMPLETED", mode = EnumSource.Mode.EXCLUDE)
    void nextStatus1(DeliveryOrderStatus input) {
        assertThat(input.nextStatus().getOrder())
            .isEqualTo(input.getOrder() + 1);
    }

    @DisplayName("주문완료 다음 상태를 찾으면 오류가 발생한다.")
    @Test
    void nextStatus2() {
        assertThatThrownBy(() -> DeliveryOrderStatus.COMPLETED.nextStatus())
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("주문 상태가 잘못 되었습니다.");
    }
}
