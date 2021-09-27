package kitchenpos.eatinorders.tobe.domain.model;

import static kitchenpos.fixture.OrderLineItemFixture.ORDER_LINE_ITEM1;
import static kitchenpos.fixture.OrderTableFixture.ORDER_TABLE1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

/*
 * - 매장 주문은 주문 항목의 수량이 0 미만일 수 있다.
 * - TODO 하나라도 숨겨진 메뉴가 있으면 주문되지 않는다. (도메인 서비스)
 * - TODO 주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다. (도메인 서비스)
 * - 주문을 접수한다.
 *  - 접수 대기 중인 주문만 접수할 수 있다.
 * - 주문을 서빙한다.
 *  - 접수된 주문만 서빙할 수 있다.
 * - 주문을 완료한다.
 *  - 매장 주문의 경우 서빙된 주문만 완료할 수 있다.
 *  - TODO 주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다. (주문 테이블 도메인 서비스)
 *  - TODO 완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다. (주문 테이블 도메인 서비스)
 */
class EatInOrderTest {

    private static final UUID ORDER_TABLE_ID = ORDER_TABLE1().getId();

    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 생성할 수 있다.")
    @Test
    void createEatInOrder() {
        final EatInOrder actual = new EatInOrder(ORDER_TABLE_ID, ORDER_LINE_ITEM1());
        assertThat(actual).isNotNull();
    }

    @DisplayName("매장 주문은 주문 항목의 수량이 0 미만일 수 있다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, -2, Long.MIN_VALUE})
    void negativeQuantityOk(final long quantity) {
        final EatInOrder actual = new EatInOrder(ORDER_TABLE_ID, ORDER_LINE_ITEM1());
        assertThat(actual).isNotNull();
    }

    @DisplayName("주문을 접수한다. - 접수 대기 중인 주문만 접수할 수 있다.")
    @ParameterizedTest
    @EnumSource(value = OrderStatus.class, names = "WAITING", mode = EnumSource.Mode.INCLUDE)
    void accept(final OrderStatus orderStatus) {
        final Order order = new EatInOrder(ORDER_TABLE_ID, orderStatus, ORDER_LINE_ITEM1());
        final Order acceptedOrder = order.accept();

        assertThat(acceptedOrder.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @DisplayName("주문을 접수한다. - 접수 대기 중인 주문이 아닌경우 IllegalStateException이 발생한다.")
    @ParameterizedTest
    @EnumSource(value = OrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    void canNotAccept(final OrderStatus orderStatus) {
        final Order order = new EatInOrder(ORDER_TABLE_ID, orderStatus, ORDER_LINE_ITEM1());
        assertThatThrownBy(() -> order.accept())
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 서빙한다. - 접수된 주문만 서빙할 수 있다.")
    @ParameterizedTest
    @EnumSource(value = OrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.INCLUDE)
    void serve(final OrderStatus orderStatus) {
        final Order order = new EatInOrder(ORDER_TABLE_ID, orderStatus, ORDER_LINE_ITEM1());
        final Order servedOrder = order.serve();
        assertThat(servedOrder.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("주문을 서빙한다. - 접수된 주문이 아닌경우 IllegalStateException이 발생한다.")
    @ParameterizedTest
    @EnumSource(value = OrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    void canNotServe(final OrderStatus orderStatus) {
        final Order order = new EatInOrder(ORDER_TABLE_ID, orderStatus, ORDER_LINE_ITEM1());
        assertThatThrownBy(() -> order.serve())
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 완료한다. - 매장 주문의 경우 서빙된 주문만 완료할 수 있다.")
    @ParameterizedTest
    @EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.INCLUDE)
    void complete(final OrderStatus orderStatus) {
        final Order order = new EatInOrder(ORDER_TABLE_ID, orderStatus, ORDER_LINE_ITEM1());
        final Order completedOrder = order.complete();
        assertThat(completedOrder.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }

    @DisplayName("주문을 완료한다. - 서빙된 주문이 아닌경우 IllegalStateException이 발생한다.")
    @ParameterizedTest
    @EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    void canNotComplete(final OrderStatus orderStatus) {
        final Order order = new EatInOrder(ORDER_TABLE_ID, orderStatus, ORDER_LINE_ITEM1());
        assertThatThrownBy(() -> order.complete())
            .isInstanceOf(IllegalStateException.class);
    }

}
