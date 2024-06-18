package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.TobeOrderTable;
import kitchenpos.eatinorders.tobe.domain.TobeOrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.constant.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.dto.event.EatInOrderCompletedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class EatInOrderEventHandler {

    private static final Logger log = LoggerFactory.getLogger(EatInOrderEventHandler.class);
    private final EatInOrderRepository orderRepository;
    private final TobeOrderTableRepository orderTableRepository;

    public EatInOrderEventHandler(
            EatInOrderRepository orderRepository,
            TobeOrderTableRepository orderTableRepository
    ) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional
    @EventListener
    public void eatInOrderCompletedEvent(EatInOrderCompletedEvent event) {
        log.info("이벤트 발생 [매장 주문 completed] , order id : {} , order table id : {}", event.orderId(), event.orderTableId());

        TobeOrderTable orderTable = orderTableRepository.findById(event.orderTableId())
                .orElseThrow(() -> new IllegalArgumentException("주문 테이블이 존재하지 않습니다."));

        if (!orderRepository.existsByOrderTableAndStatusNot(orderTable, EatInOrderStatus.COMPLETED)) {
            orderTable.clear();
        }
    }
}
