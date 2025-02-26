package kitchenpos.order.eatinorder.domain.model;

import static kitchenpos.TestFixtureFactory.createEatInOrderRequestWithEmptyTable;
import static kitchenpos.TestFixtureFactory.createMenuWithProductAndGroup;
import static kitchenpos.TestFixtureFactory.createUsingOrderTable;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kitchenpos.menu.domain.model.Menu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class EatInOrderMockTest {

    @Test
    @DisplayName("매장 주문이 완료되었다면, 주문 테이블 점유 해제 이벤트를 발행한다.")
    void publish() {
        // given
        Menu menu = createMenuWithProductAndGroup();
        EatInOrder eatInOrder = createEatInOrderRequestWithEmptyTable(menu, EatInOrderFlow.SERVED);
        OrderTable usingOrderTable = createUsingOrderTable();
        eatInOrder.occupyOrderTable(usingOrderTable);

        // when
        eatInOrder.processOrderFlow(EatInOrderStatus.COMPLETED);

        // then
        List<Object> events = getEvents(eatInOrder);
        assertThat(events)
                .hasSize(1)
                .hasOnlyElementsOfType(ReleaseOrderTableEvent.class);
    }

    @Test
    @DisplayName("완료되지 않은 매장 주문은 주문 테이블 점유 해제 이벤트를 발행하지 않는다.")
    void not_publish() {
        // given
        Menu menu = createMenuWithProductAndGroup();
        EatInOrder eatInOrder = createEatInOrderRequestWithEmptyTable(menu, EatInOrderFlow.ACCEPTED);
        OrderTable usingOrderTable = createUsingOrderTable();
        eatInOrder.occupyOrderTable(usingOrderTable);

        // when
        eatInOrder.processOrderFlow(EatInOrderStatus.SERVED);

        // then
        List<Object> events = getEvents(eatInOrder);
        assertThat(events)
                .hasSize(0)
                .hasOnlyElementsOfType(ReleaseOrderTableEvent.class);
    }

    private List<Object> getEvents(EatInOrder order) {
        return (List<Object>) ReflectionTestUtils.getField(
                order,
                AbstractAggregateRoot.class,
                "domainEvents"
        );
    }
}
