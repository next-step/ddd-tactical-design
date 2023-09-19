package kitchenpos.eatinorders.tobe.domain;

import static kitchenpos.eatinorders.tobe.domain.OrderFixture.completedOrder;
import static kitchenpos.eatinorders.tobe.domain.OrderFixture.servedOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrdersTest {

    @DisplayName("전체 주문의 완료 여부를 확인할수 있다")
    @Test
    void test1() {
        //given
        Order completedOrder1 = completedOrder();
        Order completedOrder2 = completedOrder();
        Order servedOrder = servedOrder();

        //when
        Orders completeOrders = new Orders(List.of(completedOrder1, completedOrder2));
        Orders notCompleteOrders = new Orders(List.of(completedOrder1, completedOrder2, servedOrder));

        //then
        assertAll(
            () -> assertThat(completeOrders.allOrderComplete()).isTrue(),
            () -> assertThat(notCompleteOrders.allOrderComplete()).isFalse()
        );
    }

}
