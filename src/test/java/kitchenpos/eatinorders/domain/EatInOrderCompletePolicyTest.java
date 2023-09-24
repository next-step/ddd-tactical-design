package kitchenpos.eatinorders.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kitchenpos.eatinorders.support.InMemoryEatInOrderRepository;

class EatInOrderCompletePolicyTest {
    private final EatInOrderRepository eatInOrderRepository = new InMemoryEatInOrderRepository();
    private final EatInOrderCompletePolicy eatInOrderCompletePolicy = new EatInOrderCompletePolicy(eatInOrderRepository);

    private OrderLineItems orderLineItems;
    private OrderTable orderTable;
    private EatInOrder notCompletedEatInOrder;

    @BeforeEach
    void init() {
        orderLineItems = new OrderLineItems(List.of(new OrderLineItem(UUID.randomUUID(), 1, 1000L)));

        orderTable = new OrderTable(UUID.randomUUID(), "1번 테이블");
        orderTable.use();

        notCompletedEatInOrder = new EatInOrder(UUID.randomUUID(), orderLineItems, orderTable);

        eatInOrderRepository.save(notCompletedEatInOrder);
    }

    @Test
    void 주문테이블의_모든_주문이_완료되지않았으면_테이블을_정리하지_않는다() {
        eatInOrderCompletePolicy.follow(orderTable);

        assertThat(orderTable.isInUse()).isTrue();
    }

    @Test
    void 주문테이블의_모든_주문이_완료됨이면_테이블을_정리한다() {
        notCompletedEatInOrder.accept();
        notCompletedEatInOrder.serve();

        notCompletedEatInOrder.complete(eatInOrderCompletePolicy); // eatInOrderCompletePolicy.follow(orderTable);

        assertThat(orderTable.isNotInUse()).isTrue();
    }
}
