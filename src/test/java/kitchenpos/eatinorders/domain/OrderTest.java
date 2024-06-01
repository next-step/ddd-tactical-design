package kitchenpos.eatinorders.domain;

import kitchenpos.eatinorders.EatInOrderFixture;
import kitchenpos.eatinorders.tobe.constant.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.constant.EatInOrderType;
import kitchenpos.eatinorders.tobe.entity.OrderLineItem;
import kitchenpos.eatinorders.tobe.entity.OrderTable;
import kitchenpos.eatinorders.tobe.entity.EatInOrder;
import kitchenpos.eatinorders.tobe.entity.OrderLineItems;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("주문 도메인 테스트")
public class OrderTest {

    @Test
    @DisplayName("매장 식사 주문을 생성한다.")
    void create() {
        EatInOrder eatInOrder = EatInOrderFixture.eatInOrderOf(createDefaultOrderLineItems(), UUID.randomUUID());

        assertAll(
                () -> Assertions.assertThat(eatInOrder.getId()).isNotNull(),
                () -> Assertions.assertThat(eatInOrder.getType()).isEqualTo(EatInOrderType.EAT_IN),
                () -> Assertions.assertThat(eatInOrder.getStatus()).isEqualTo(EatInOrderStatus.WAITING)
        );
    }

    @Test
    @DisplayName("주문을 접수한다.")
    void accept() {
        EatInOrder 매장_식사 = EatInOrderFixture.eatInOrderOf(createDefaultOrderLineItems(), UUID.randomUUID());

        매장_식사.accept();

        Assertions.assertThat(매장_식사.getStatus() == EatInOrderStatus.ACCEPTED).isTrue();
    }

    @Test
    @DisplayName("주문을 서빙한다.")
    void serve() {
        EatInOrder 매장_식사 = EatInOrderFixture.eatInOrderOf(createDefaultOrderLineItems(), UUID.randomUUID());
        매장_식사.accept();

        매장_식사.serve();

        Assertions.assertThat(매장_식사.getStatus() == EatInOrderStatus.SERVED).isTrue();
    }

    @Test
    @DisplayName("주문을 완료한다.")
    void complete() {
        EatInOrder 매장_식사 = EatInOrderFixture.eatInOrderOf(createDefaultOrderLineItems(), UUID.randomUUID());
        매장_식사.accept();
        매장_식사.serve();

        매장_식사.complete();

        Assertions.assertThat(매장_식사.getStatus() == EatInOrderStatus.COMPLETED).isTrue();
    }

    @Test
    @DisplayName("주문을 완료하여 빈 테이블로 설정한다.")
    void complete_orderTable () {
        OrderTable 주문_테이블 = EatInOrderFixture.sitOrderTableOf("주문_테이블");
        Assertions.assertThat(주문_테이블.isNotOccupied()).isTrue();

        EatInOrder 매장_식사 = EatInOrderFixture.eatInOrderOf(createDefaultOrderLineItems(), 주문_테이블.getId());
        매장_식사.accept();
        매장_식사.serve();
        매장_식사.complete();

        assertAll(
                () -> Assertions.assertThat(주문_테이블.getNumberOfGuests()).isZero(),
                () -> Assertions.assertThat(주문_테이블.isNotOccupied()).isTrue()
        );
    }

    private OrderLineItems createDefaultOrderLineItems() {
        OrderLineItem orderLineItem1 = EatInOrderFixture.orderLineItemOf(5, BigDecimal.valueOf(10_000));
        OrderLineItem orderLineItem2 = EatInOrderFixture.orderLineItemOf(5, BigDecimal.valueOf(10_000));
        return new OrderLineItems(List.of(orderLineItem1, orderLineItem2));
    }
}
