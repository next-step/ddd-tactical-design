package kitchenpos.eatinorders.order.tobe.domain;

import kitchenpos.eatinorders.ordertable.tobe.domain.InMemoryOrderTableRepository;
import kitchenpos.eatinorders.ordertable.tobe.domain.OrderTable;
import kitchenpos.eatinorders.ordertable.tobe.domain.OrderTableCleanUp;
import kitchenpos.eatinorders.ordertable.tobe.domain.OrderTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class EatInOrderTest {

    private EatInOrderRepository eatInOrderRepository;
    private OrderTableRepository orderTableRepository;
    private OrderTableCleanUpPolicy orderTableCleanUpPolicy;
    private OrderTableCleanUp orderTableCleanUp;

    private UUID orderTableId;

    @BeforeEach
    void setUp() {
        eatInOrderRepository = new InMemoryEatInOrderRepository();
        orderTableRepository = new InMemoryOrderTableRepository();
        orderTableCleanUpPolicy = new OrderTableCleanUpPolicy(eatInOrderRepository);
        orderTableCleanUp = new OrderTableCleanUp(orderTableCleanUpPolicy, orderTableRepository);

        final OrderTable orderTable = OrderTable.createEmptyTable("1번 테이블");
        orderTable.use();
        orderTableRepository.save(orderTable);
        orderTableId = orderTable.id();
    }

    @DisplayName("매장주문을 생성한다.")
    @Test
    void create() {
        final EatInOrderLineItem eatInOrderLineItem1 = EatInOrderLineItem.create(UUID.randomUUID(), 15_000L, 2);
        final EatInOrderLineItem eatInOrderLineItem2 = EatInOrderLineItem.create(UUID.randomUUID(), 25_000L, 1);
        final EatInOrderLineItems eatInOrderLineItems = EatInOrderLineItems.of(eatInOrderLineItem1, eatInOrderLineItem2);

        final EatInOrder eatInOrder = EatInOrder.create(UUID.randomUUID(), eatInOrderLineItems);

        assertAll(
                () -> assertThat(eatInOrder.id()).isNotNull(),
                () -> assertThat(eatInOrder.orderTableId()).isNotNull(),
                () -> assertThat(eatInOrder.orderDateTime()).isNotNull(),
                () -> assertThat(eatInOrder.status()).isEqualTo(EatInOrderStatus.WAITING),
                () -> assertThat(eatInOrder.eatInOrderLineItems()).isNotNull()
        );
    }

    @DisplayName("매장 주문을 수락한다.")
    @Nested
    class AcceptTest {

        @DisplayName("성공")
        @Test
        void success() {
            final EatInOrderLineItem eatInOrderLineItem1 = EatInOrderLineItem.create(UUID.randomUUID(), 15_000L, 2);
            final EatInOrderLineItem eatInOrderLineItem2 = EatInOrderLineItem.create(UUID.randomUUID(), 25_000L, 1);
            final EatInOrderLineItems eatInOrderLineItems = EatInOrderLineItems.of(eatInOrderLineItem1, eatInOrderLineItem2);
            final EatInOrder eatInOrder = EatInOrder.create(UUID.randomUUID(), eatInOrderLineItems);

            eatInOrder.accept();

            assertThat(eatInOrder.status()).isEqualTo(EatInOrderStatus.ACCEPTED);
        }

        @ParameterizedTest(name = "상태가 대기상태일 때만 매장 주문을 수락할 수 있다. status={0}")
        @EnumSource(
                value = EatInOrderStatus.class,
                names = {"WAITING"},
                mode = EnumSource.Mode.EXCLUDE)
        void error(final EatInOrderStatus status) {
            final EatInOrder eatInOrder = EatInOrderFixture.create(UUID.randomUUID(), UUID.randomUUID(), status);

            assertThatThrownBy(() -> eatInOrder.accept())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("주문 상태가 대기일 때만 가능합니다.");
        }
    }

    @DisplayName("매장 주문을 서빙한다.")
    @Nested
    class ServeTest {

        @DisplayName("성공")
        @Test
        void success() {
            final EatInOrderLineItem eatInOrderLineItem1 = EatInOrderLineItem.create(UUID.randomUUID(), 15_000L, 2);
            final EatInOrderLineItem eatInOrderLineItem2 = EatInOrderLineItem.create(UUID.randomUUID(), 25_000L, 1);
            final EatInOrderLineItems eatInOrderLineItems = EatInOrderLineItems.of(eatInOrderLineItem1, eatInOrderLineItem2);
            final EatInOrder eatInOrder = EatInOrder.create(UUID.randomUUID(), eatInOrderLineItems);
            eatInOrder.accept();

            eatInOrder.serve();

            assertThat(eatInOrder.status()).isEqualTo(EatInOrderStatus.SERVED);
        }

        @ParameterizedTest(name = "상태가 대기상태일 때만 매장 주문을 수락할 수 있다. status={0}")
        @EnumSource(
                value = EatInOrderStatus.class,
                names = {"ACCEPTED"},
                mode = EnumSource.Mode.EXCLUDE)
        void error(final EatInOrderStatus status) {
            final EatInOrder eatInOrder = EatInOrderFixture.create(UUID.randomUUID(), UUID.randomUUID(), status);

            assertThatThrownBy(() -> eatInOrder.serve())
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("주문 상태가 수락일 때만 가능합니다.");
        }
    }

    @DisplayName("매장 주문을 완료한다.")
    @Nested
    class CompleteTest {

        @DisplayName("성공")
        @Test
        void success() {
            final EatInOrderLineItem eatInOrderLineItem1 = EatInOrderLineItem.create(UUID.randomUUID(), 15_000L, 2);
            final EatInOrderLineItem eatInOrderLineItem2 = EatInOrderLineItem.create(UUID.randomUUID(), 25_000L, 1);
            final EatInOrderLineItems eatInOrderLineItems = EatInOrderLineItems.of(eatInOrderLineItem1, eatInOrderLineItem2);
            final EatInOrder eatInOrder = EatInOrder.create(orderTableId, eatInOrderLineItems);
            eatInOrder.accept();
            eatInOrder.serve();

            eatInOrder.complete(orderTableCleanUpPolicy, orderTableCleanUp);

            assertThat(eatInOrder.status()).isEqualTo(EatInOrderStatus.COMPLETED);
        }

        @ParameterizedTest(name = "상태가 대기상태일 때만 매장 주문을 수락할 수 있다. status={0}")
        @EnumSource(
                value = EatInOrderStatus.class,
                names = {"SERVED"},
                mode = EnumSource.Mode.EXCLUDE)
        void error(final EatInOrderStatus status) {
            final EatInOrder eatInOrder = EatInOrderFixture.create(orderTableId, UUID.randomUUID(), status);

            assertThatThrownBy(() -> eatInOrder.complete(orderTableCleanUpPolicy, orderTableCleanUp))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("주문 상태가 서빙완료일 때만 가능합니다.");
        }
    }
}
