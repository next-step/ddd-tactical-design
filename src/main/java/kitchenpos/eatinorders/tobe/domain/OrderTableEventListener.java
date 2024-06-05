package kitchenpos.eatinorders.tobe.domain;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class OrderTableEventListener {

    private final OrderTableRepository orderTableRepository;

    public OrderTableEventListener(OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }


    @EventListener
    public void clearTable(EatInOrderCompletedEvent event){
        UUID orderTableId = event.orderTableId();
        OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(IllegalArgumentException::new);

        orderTable.changeNumberOfGuests(0);
        orderTable.clear();

        orderTableRepository.save(orderTable);


    }


}
