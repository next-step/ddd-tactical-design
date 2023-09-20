package kitchenpos.ordertables.subsriber;

import kitchenpos.eatinorders.domain.EatInOrderStatus;
import kitchenpos.eatinorders.publisher.EatInOrderCompletedEvent;
import kitchenpos.ordertables.domain.OrderTable;
import kitchenpos.ordertables.domain.OrderTableId;
import kitchenpos.ordertables.domain.OrderTableRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.NoSuchElementException;

@Component
public class EatInOrderCompletedEventListener {

    private final OrderTableRepository orderTableRepository;

    public EatInOrderCompletedEventListener(OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void listen(EatInOrderCompletedEvent event) {
        OrderTableId orderTableId = new OrderTableId(event.getOrderTableId());

        if (orderTableRepository.existsByOrderAndStatusNot(orderTableId, EatInOrderStatus.COMPLETED)) {
            return;
        }

        OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);

        orderTable.clear();
    }

}
