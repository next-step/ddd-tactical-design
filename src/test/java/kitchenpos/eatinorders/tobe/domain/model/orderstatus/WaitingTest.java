package kitchenpos.eatinorders.tobe.domain.model.orderstatus;

import kitchenpos.eatinorders.tobe.domain.model.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WaitingTest {

    @DisplayName("접수 대기 상태를 생성한다.")
    @Test
    void 생성_성공() {
        final OrderStatus orderStatus = new Waiting();

        assertThat(orderStatus.getStatus()).isEqualTo("Waiting");
    }

    @DisplayName("접수 대기는 접수로 진행된다.")
    @Test
    void 상태_진행_성공() {
        final OrderStatus orderStatus = new Waiting();

        assertThat(orderStatus.proceed()).isInstanceOf(Accepted.class);
    }
}
