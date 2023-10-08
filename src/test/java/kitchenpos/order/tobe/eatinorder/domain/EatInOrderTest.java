package kitchenpos.order.tobe.eatinorder.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kitchenpos.Fixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class EatInOrderTest {

    @DisplayName("매장 주문을 수락한다")
    @Test
    void testAccept() {
        // given
        EatInOrder eatInOrder = Fixtures.eatInOrder(EatInOrderStatus.WAITING);

        // when
        eatInOrder.accept();

        // then
        assertThat(eatInOrder.getStatus()).isSameAs(EatInOrderStatus.ACCEPTED);
    }

    @DisplayName("대기중인 매장 주문이 아니면 매장 주문을 수락할 수 없다")
    @ParameterizedTest
    @EnumSource(value = EatInOrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    void testAcceptIfNotValidBeforeStatus(EatInOrderStatus status) {
        // given
        EatInOrder eatInOrder = Fixtures.eatInOrder(status);

        // when // then
        assertThatThrownBy(eatInOrder::accept)
            .isExactlyInstanceOf(IllegalStateException.class);
    }

    @DisplayName("매장 주문을 서빙한다")
    @Test
    void testServe() {
        // given
        EatInOrder eatInOrder = Fixtures.eatInOrder(EatInOrderStatus.ACCEPTED);

        // when
        eatInOrder.serve();

        // then
        assertThat(eatInOrder.getStatus()).isSameAs(EatInOrderStatus.SERVED);
    }

    @DisplayName("수락된 매장 주문이 아니라면 매장 주문을 서빙할 수 없다")
    @ParameterizedTest
    @EnumSource(value = EatInOrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    void testServeIfNotValidBeforeStatus(EatInOrderStatus status) {
        // given
        EatInOrder eatInOrder = Fixtures.eatInOrder(status);

        // when // then
        assertThatThrownBy(eatInOrder::serve)
            .isExactlyInstanceOf(IllegalStateException.class);
    }

    @DisplayName("매장 주문을 완료처리 한다")
    @Test
    void testComplete() {
        // given
        EatInOrder eatInOrder = Fixtures.eatInOrder(EatInOrderStatus.SERVED);

        // when
        eatInOrder.complete();

        // then
        assertThat(eatInOrder.getStatus()).isSameAs(EatInOrderStatus.COMPLETED);
    }

    @DisplayName("서빙된 매장 주문이 아니라면 매장 주문을 완료처리 할 수 없다")
    @ParameterizedTest
    @EnumSource(value = EatInOrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    void testCompleteIfNotValidBeforeStatus(EatInOrderStatus status) {
        // given
        EatInOrder eatInOrder = Fixtures.eatInOrder(status);

        // when // then
        assertThatThrownBy(eatInOrder::complete)
            .isExactlyInstanceOf(IllegalStateException.class);
    }
}
