package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.domain.OrderStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static kitchenpos.eatinorders.tobe.domain.fixture.OrderFixture.createEatInOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/*
공통
- [x] 주문 유형이 올바르지 않으면 등록할 수 없다.
- [x] 메뉴가 없으면 등록할 수 없다.
- [x] 숨겨진 메뉴는 주문할 수 없다.
- [x] 접수 대기 중인 주문만 접수할 수 있다.
- [x] 접수된 주문만 서빙할 수 있다.
- [] 주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.

매장
- [x] 매장 주문은 주문 항목의 수량이 0 미만일 수 있다.
- [x] 매장 주문의 경우 서빙된 주문만 완료할 수 있다.
- [x] 1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.
- [x] 주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.
- [x] 완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.
- [x] 빈 테이블에는 매장 주문을 등록할 수 없다.
*/
public class EatInOrderTest {
    @DisplayName("매장 주문 등록")
    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {
            "주문 유형이 올바르지 않으면 등록할 수 없다.",
            "메뉴가 없으면 등록할 수 없다.",
            "숨겨진 메뉴는 주문할 수 없다.",
            "접수 대기 중인 주문만 접수할 수 있다.",
            "매장 주문은 주문 항목의 수량이 0 미만일 수 있다.",
            "빈 테이블에는 매장 주문을 등록할 수 없다."
    })
    void registerWithEatIn(String message) {
        assertThatCode(() -> createEatInOrder())
                .doesNotThrowAnyException();
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @Test
    void serve() {
        final Order order = createEatInOrder();
        order.accept();

        order.serve();

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
    @Test
    void completeAndEmpty() {
        final Order eatInOrder = createEatInOrder();
        eatInOrder.accept();
        eatInOrder.serve();

        eatInOrder.complete();

        assertThat(eatInOrder.getOrderTable().isOccupied()).isFalse();
    }
}
