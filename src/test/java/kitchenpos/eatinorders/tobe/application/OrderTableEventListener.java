package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.order.InMemoryEatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.order.event.EatInOrderCompletedEvent;
import kitchenpos.eatinorders.tobe.domain.ordertable.InMemoryOrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.ordertable.vo.OrderTableId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 기능 요건에 따라 UI/Application/Infra 에 둘 수 있음
 * 개인적으로 HttpClient 를 사용하여 API 통신을 선호하나
 * 동기식 요건이 아니라 판단이 들고 결과적으로만 일관성을 맞추면 될 것 같아서 이벤트로 분리
 */
public class OrderTableEventListener {
    private static final Logger logger = LoggerFactory.getLogger(OrderTableEventListener.class);

    private final OrderTableService orderTableService = new OrderTableService(
            new InMemoryOrderTableRepository(),
            new InMemoryEatInOrderRepository()
    );

    // @EventListener
    // @Async + @EnableAsync
    public void handle(final EatInOrderCompletedEvent event) {
        logger.debug("매장 주문이 완료처리 되었습니다.: {}", event.eventId());
        final OrderTableId orderTableId = event.orderTableId();
        orderTableService.clear(orderTableId.getValue());
    }
}
