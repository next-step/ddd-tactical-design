package kitchenpos.eatinorders.domain;

import kitchenpos.eatinorders.EatInOrderFixture;
import kitchenpos.eatinorders.tobe.domain.constant.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.domain.constant.EatInOrderType;
import kitchenpos.eatinorders.tobe.domain.entity.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.entity.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.entity.OrderLineItems;
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

    private OrderLineItems createDefaultOrderLineItems() {
        OrderLineItem orderLineItem1 = EatInOrderFixture.orderLineItemOf(5, BigDecimal.valueOf(10_000));
        OrderLineItem orderLineItem2 = EatInOrderFixture.orderLineItemOf(5, BigDecimal.valueOf(10_000));
        return new OrderLineItems(List.of(orderLineItem1, orderLineItem2));
    }
}
