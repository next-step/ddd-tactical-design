package kitchenpos.eatinorders.tobe.domain;

import static kitchenpos.eatinorders.tobe.OrderFixtures.eatInOrder;
import static kitchenpos.eatinorders.tobe.OrderFixtures.eatInOrderOfAccepted;
import static kitchenpos.eatinorders.tobe.OrderFixtures.eatInOrderOfServed;
import static kitchenpos.eatinorders.tobe.OrderFixtures.menu;
import static kitchenpos.eatinorders.tobe.OrderFixtures.numberOfGuests;
import static kitchenpos.eatinorders.tobe.OrderFixtures.orderLineItem;
import static kitchenpos.eatinorders.tobe.OrderFixtures.orderLineItems;
import static kitchenpos.eatinorders.tobe.OrderFixtures.orderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import kitchenpos.eatinorders.tobe.domain.vo.DisplayedMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
    @DisplayName("주문을 접수한다.")
    @Test
    void accept() {
        EatInOrder actual = eatInOrder(orderLineItem(1, menu));

        actual.accept();

        assertThat(actual.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        EatInOrder actual = eatInOrderOfAccepted(orderTable);

        actual.serve();

        assertThat(actual.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @DisplayName("주문 완료 상태 변경")
    @Nested
    class CompleteTest {

        @DisplayName("주문을 완료한다. "
                + "주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
        @Test
        void success() {
            EatInOrder actual = eatInOrderOfServed(orderTable);

            actual.complete();

            assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED);
            assertAll(
                    () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED),
                    () -> assertThat(actual.getOrderTable().isEmpty()).isTrue()
            );
        }

        @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
        @Test
        void completeEatInOrder() {
            EatInOrder actual = eatInOrderOfServed(orderTable);

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
