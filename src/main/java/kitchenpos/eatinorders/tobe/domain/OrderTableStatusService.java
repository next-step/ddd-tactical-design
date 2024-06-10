package kitchenpos.eatinorders.tobe.domain;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderTableStatusService {

    private final OrderTableRepository orderTableRepository;

    public OrderTableStatusService(OrderTableRepository orderTableRepository) {
        this.orderTableRepository = orderTableRepository;
    }

    public boolean isCleared(UUID orderTableId) {
        OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(IllegalArgumentException::new);

        return !orderTable.isOccupied();
    }
}
