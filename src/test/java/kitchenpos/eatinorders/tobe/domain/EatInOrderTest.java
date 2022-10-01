package kitchenpos.eatinorders.tobe.domain;

import static kitchenpos.eatinorders.tobe.OrderFixtures.eatInOrder;
import static kitchenpos.eatinorders.tobe.OrderFixtures.menu;
import static kitchenpos.eatinorders.tobe.OrderFixtures.numberOfGuests;
import static kitchenpos.eatinorders.tobe.OrderFixtures.orderLineItem;
import static kitchenpos.eatinorders.tobe.OrderFixtures.orderLineItems;
import static kitchenpos.eatinorders.tobe.OrderFixtures.orderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;
import kitchenpos.eatinorders.tobe.domain.vo.DisplayedMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;

class EatInOrderTest {

    private DisplayedMenu menu;
    private OrderTable orderTable;

    @BeforeEach
    void setUp() {
        menu = menu("양념치킨", 19_000L);
        orderTable = orderTable(4, true);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Test
    void create() {
        EatInOrder expected = new EatInOrder(
                orderLineItems(orderLineItem(1, menu)),
                orderTable
        );

        assertThat(expected).isNotNull();
        assertAll(
                () -> assertThat(expected.getId()).isNotNull(),
                () -> assertThat(expected.getType()).isEqualTo(expected.getType()),
                () -> assertThat(expected.getStatus()).isEqualTo(OrderStatus.WAITING),
                () -> assertThat(expected.getOrderDateTime()).isNotNull(),
                () -> assertThat(expected.getOrderLineItems().getValues()).hasSize(1),
                () -> assertThat(expected.getOrderTable().getId()).isNotNull()
        );
    }

    @DisplayName("매장 주문 생성 예외 케이스")
    @Nested
    class CreateErrorCaseTest {

        @DisplayName("주문 유형이 올바르지 않으면 등록할 수 없다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @NullSource
        @EnumSource(value = OrderType.class, names = "EAT_IN", mode = EnumSource.Mode.EXCLUDE)
        void error1(OrderType type) {
            assumeTrue(OrderType.EAT_IN != type);

            assertThatThrownBy(() -> new EatInOrder(
                    type,
                    orderLineItems(orderLineItem(3L, menu)),
                    orderTable
            )).isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
        @Test
        void error2() {
            orderTable.clear();

            assertThatThrownBy(() -> new EatInOrder(
                    orderLineItems(orderLineItem(1, menu)),
                    orderTable)
            ).isInstanceOf(IllegalStateException.class);
        }
    }

    @DisplayName("접수 상태 변경")
    @Nested
    class AcceptTest {

        @DisplayName("주문을 접수한다.")
        @Test
        void success() {
            EatInOrder actual = eatInOrder(OrderStatus.WAITING, orderTable);

            actual.accept();

            assertThat(actual.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
        }

        @DisplayName("접수 대기 중인 주문만 접수할 수 있다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @EnumSource(value = OrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
        void error(OrderStatus status) {
            EatInOrder actual = eatInOrder(status, orderTable);

            assertThatThrownBy(() -> actual.accept())
                    .isInstanceOf(IllegalStateException.class);
        }
    }

    @DisplayName("서빙 상태 변경")
    @Nested
    class ServeTest {

        @DisplayName("주문을 서빙한다.")
        @Test
        void success() {
            EatInOrder actual = eatInOrder(OrderStatus.ACCEPTED, orderTable);

            actual.serve();

            assertThat(actual.getStatus()).isEqualTo(OrderStatus.SERVED);
        }

        @DisplayName("접수된 주문만 서빙할 수 있다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @EnumSource(value = OrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
        void error(OrderStatus status) {
            EatInOrder actual = eatInOrder(status, orderTable);

            assertThatThrownBy(() -> actual.serve())
                    .isInstanceOf(IllegalStateException.class);
        }
    }

    @DisplayName("주문 완료 상태 변경")
    @Nested
    class CompleteTest {

        @DisplayName("주문을 완료한다. "
                + "주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
        @Test
        void success() {
            EatInOrder actual = eatInOrder(OrderStatus.SERVED, orderTable);

            actual.complete();

            assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED);
            assertAll(
                    () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED),
                    () -> assertThat(actual.getOrderTable().isEmpty()).isTrue()
            );
        }

        @DisplayName("서빙된 주문만 완료할 수 있다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @EnumSource(value = OrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
        void error(final OrderStatus status) {
            EatInOrder actual = eatInOrder(status, orderTable);

            assertThatThrownBy(() -> actual.complete())
                    .isInstanceOf(IllegalStateException.class);
        }

        @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
        @Test
        void completeEatInOrder() {
            EatInOrder actual = eatInOrder(OrderStatus.SERVED, orderTable);

            actual.complete();

            assertAll(
                    () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED),
                    () -> assertThat(orderTable.isEmpty()).isTrue(),
                    () -> assertThat(orderTable.isOccupied()).isFalse(),
                    () -> assertThat(orderTable.getNumberOfGuests()).isEqualTo(numberOfGuests(0))
            );
        }
    }
}
