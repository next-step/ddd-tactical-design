package kitchenpos.orders.tobe.domain;

import static kitchenpos.TobeFixtures.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;

import kitchenpos.common.tobe.infra.TestContainer;

class EatInOrderTest {
    private OrderTableRepository orderTableRepository;
    private KitchenridersClient kitchenridersClient;

    @BeforeEach
    void setUp() {
        TestContainer testContainer = new TestContainer();
        orderTableRepository = testContainer.orderTableRepository;
        kitchenridersClient = testContainer.kitchenridersClient;
    }

    @DisplayName("매장 주문을 생성할 수 있다.")
    @Test
    void createEatInOrder() {
        // given
        final OrderTable orderTable = orderTableRepository.save(orderTable(4, true));

        // when
        final EatInOrder eatInOrder = eatInOrder(OrderStatus.WAITING, orderTable);

        // then
        assertThat(eatInOrder).isNotNull();
        assertThat(eatInOrder.getType()).isEqualTo(OrderType.EAT_IN);
        assertThat(eatInOrder.getOrderTable()).isEqualTo(orderTable);
    }

    @DisplayName("주문 테이블이 없으면 매장 주문을 생성할 수 없다.")
    @NullSource
    @ParameterizedTest
    void createEatInOrderWithoutOrderTable(final OrderTable orderTable) {
        // given

        // when // then
        assertThatThrownBy(() -> eatInOrder(OrderStatus.WAITING, orderTable))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("주문 테이블이 사용 중이 아니면 매장 주문을 생성할 수 없다.")
    @Test
    void createEatInOrderWithEmptyTable() {
        // given
        final OrderTable orderTable = orderTableRepository.save(orderTable(0, false));

        // when // then
        assertThatThrownBy(() -> eatInOrder(OrderStatus.WAITING, orderTable))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("접수 대기 중인 매장 주문만 접수할 수 있다.")
    @EnumSource(value = OrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void acceptInvalidStatusOrder(OrderStatus status) {
        // given
        final OrderTable orderTable = orderTableRepository.save(orderTable(4, true));
        final EatInOrder eatInOrder = eatInOrder(status, orderTable);

        // when // then
        assertThatThrownBy(() -> eatInOrder.accepted(kitchenridersClient))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 접수한다.")
    @Test
    void acceptOrder() {
        // given
        final OrderTable orderTable = orderTableRepository.save(orderTable(4, true));
        final EatInOrder eatInOrder = eatInOrder(OrderStatus.WAITING, orderTable);

        // when
        eatInOrder.accepted(kitchenridersClient);

        // then
        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @DisplayName("접수된 매장 주문을 서빙한다.")
    @Test
    void serveOrder() {
        // given
        final OrderTable orderTable = orderTableRepository.save(orderTable(4, true));
        final EatInOrder eatInOrder = eatInOrder(OrderStatus.ACCEPTED, orderTable);

        // when
        eatInOrder.served();

        // then
        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("서빙된 매장 주문을 완료한다.")
    @Test
    void completeOrder() {
        // given
        final OrderTable orderTable = orderTableRepository.save(orderTable(4, true));
        final EatInOrder eatInOrder = eatInOrder(OrderStatus.SERVED, orderTable);

        // when
        eatInOrder.completed();

        // then
        assertThat(eatInOrder.getStatus()).isEqualTo(OrderStatus.COMPLETED);
    }
}
