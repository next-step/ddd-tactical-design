package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kitchenpos.eatinorders.tobe.domain.EatInOrderFixture.eatInOrder;
import static kitchenpos.eatinorders.tobe.domain.OrderStatus.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class EatInOrderTest {

    @DisplayName("매장 주문을 생성한다")
    @Test
    void createEatInOrder() {
        assertThatCode(EatInOrderFixture::eatInOrder)
                .doesNotThrowAnyException();
    }

    @DisplayName("매장 주문을 생성하면 주문 테이블에 쌓인다")
    @Test
    void addEatInOrder() {
        // given
        OrderTable orderTable = new OrderTable("1번");
        orderTable.sit();
        orderTable.changeNumberOfGuests(3);

        // when
        eatInOrder(ACCEPTED, orderTable);
        eatInOrder(ACCEPTED, orderTable);
        eatInOrder(ACCEPTED, orderTable);

        // then
        assertThat(orderTable.getEatInOrders()).hasSize(3);
    }

    @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다")
    @Test
    void createEatInOrderWithEmptyTable() {
        // given
        OrderTable orderTable = new OrderTable("1번");

        // when, then
        assertThatThrownBy(() -> eatInOrder(orderTable))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("빈 테이블에는 매장 주문을 등록할 수 없습니다.");
    }

    @DisplayName("매장 주문을 접수한다")
    @Test
    void accept() {
        // given
        EatInOrder eatInOrder = eatInOrder();

        // when
        eatInOrder.accept();

        // then
        assertThat(eatInOrder.getStatus()).isEqualTo(ACCEPTED);
    }

    @DisplayName("접수 대기 중인 주문만 접수할 수 있다")
    @Test
    void acceptNonWaiting() {
        // given
        EatInOrder eatInOrder = eatInOrder(ACCEPTED);

        // when, then
        assertThatThrownBy(eatInOrder::accept)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("접수 대기중인 주문만 접수할 수 있습니다.");
    }

    @DisplayName("매장 주문을 서빙한다")
    @Test
    void serve() {
        // given
        EatInOrder eatInOrder = eatInOrder(ACCEPTED);

        // when
        eatInOrder.serve();

        // then
        assertThat(eatInOrder.getStatus()).isEqualTo(SERVED);
    }

    @DisplayName("매장 주문은 접수된 상태만 서빙할 수 있다")
    @Test
    void serveNotAccepted() {
        // given
        EatInOrder eatInOrder = eatInOrder(SERVED);

        // when, then
        assertThatThrownBy(eatInOrder::serve)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("매장 주문은 접수된 상태만 주문 서빙할 수 있습니다.");
    }

    @DisplayName("매장 주문을 완료한다")
    @Test
    void complete() {
        // given
        EatInOrder eatInOrder = eatInOrder(SERVED);

        // when
        eatInOrder.complete();

        // then
        assertThat(eatInOrder.getStatus()).isEqualTo(COMPLETED);
    }

    @DisplayName("매장 주문은 접수된 상태만 주문 완료할 수 있다")
    @Test
    void completeNotServed() {
        // given
        EatInOrder eatInOrder = eatInOrder(COMPLETED);

        // when, then
        assertThatThrownBy(eatInOrder::complete)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("매장 주문은 서빙된 상태만 주문 완료할 수 있습니다.");
    }

    @DisplayName("매장 주문이 모두 완료되면 주문 테이블은 빈 테이블이 된다")
    @Test
    void completeAndSetEmptyTable() {
        // given
        OrderTable orderTable = new OrderTable("1번");
        orderTable.sit();
        orderTable.changeNumberOfGuests(3);

        eatInOrder(COMPLETED, orderTable);
        eatInOrder(COMPLETED, orderTable);
        EatInOrder eatInOrder = eatInOrder(SERVED, orderTable);

        // when
        eatInOrder.complete();

        // then
        assertAll(
                () -> assertThat(eatInOrder.getStatus()).isEqualTo(COMPLETED),
                () -> assertThat(orderTable.getNumberOfGuests()).isEqualTo(new NumberOfGuests(0)),
                () -> assertThat(orderTable.isEmpty()).isTrue()
        );
    }

    @DisplayName("매장 주문이 모두 완료되지 않았으면 주문 테이블은 빈 테이블이 되지 않는다")
    @Test
    void completeKeepOrderTable() {
        // given
        OrderTable orderTable = new OrderTable("1번");
        orderTable.sit();
        orderTable.changeNumberOfGuests(3);

        eatInOrder(COMPLETED, orderTable);
        eatInOrder(SERVED, orderTable);
        EatInOrder eatInOrder = eatInOrder(SERVED, orderTable);

        // when
        eatInOrder.complete();

        // then
        assertAll(
                () -> assertThat(eatInOrder.getStatus()).isEqualTo(COMPLETED),
                () -> assertThat(orderTable.getNumberOfGuests()).isEqualTo(new NumberOfGuests(3)),
                () -> assertThat(orderTable.isEmpty()).isFalse()
        );
    }
}
