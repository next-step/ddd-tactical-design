package kitchenpos.eatinorders.tobe.domain.model.orderstatus;

import kitchenpos.eatinorders.tobe.domain.model.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ServedTest {

    @DisplayName("서빙 상태를 생성한다.")
    @Test
    void 생성_성공() {
        final OrderStatus orderStatus = new Served();

        assertThat(orderStatus.getStatus()).isEqualTo("Served");
    }

    @DisplayName("서빙은 계산 완료로 진행된다.")
    @Test
    void 상태_진행_성공() {
        final OrderStatus orderStatus = new Served();

        assertThat(orderStatus.proceed()).isEqualTo(new Completed());
    }
}
