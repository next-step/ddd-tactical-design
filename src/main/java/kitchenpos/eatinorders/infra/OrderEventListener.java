package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.domain.EatInOrderTable;
import kitchenpos.eatinorders.domain.EatInOrderTableRepository;
import kitchenpos.eatinorders.domain.OrderCompleteEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

import static kitchenpos.eatinorders.exception.OrderTableExceptionMessage.NOT_FOUND_ORDER_TABLE;

@Component
public class OrderEventListener {

    private final EatInOrderTableRepository eatInOrderTableRepository;

    public OrderEventListener(EatInOrderTableRepository eatInOrderTableRepository) {
        this.eatInOrderTableRepository = eatInOrderTableRepository;
    }

    @EventListener
    public void onOrderCompleteEventHandler(OrderCompleteEvent event) {
        EatInOrderTable eatInOrderTable = eatInOrderTableRepository.findById(event.getOrderTableId())
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_ORDER_TABLE));
        eatInOrderTable.clear();
    }
}
