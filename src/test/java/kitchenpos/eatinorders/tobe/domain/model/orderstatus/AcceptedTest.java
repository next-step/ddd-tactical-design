package kitchenpos.eatinorders.tobe.domain.model.orderstatus;

import kitchenpos.eatinorders.tobe.domain.model.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AcceptedTest {

    @DisplayName("접수 상태를 생성한다.")
    @Test
    void 생성_성공() {
        final OrderStatus orderStatus = new Accepted();

        assertThat(orderStatus.getStatus()).isEqualTo("Accepted");
    }

    @DisplayName("접수는 서빙으로 진행된다.")
    @Test
    void 상태_진행_성공() {
        final OrderStatus orderStatus = new Accepted();

        assertThat(orderStatus.proceed()).isEqualTo(new Served());
    }
}
