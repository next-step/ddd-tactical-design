package kitchenpos.eatinorders.tobe.domain;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderStatusService {

    private final EatInOrderRepository eatInOrderRepository;

    public OrderStatusService(EatInOrderRepository eatInOrderRepository) {
        this.eatInOrderRepository = eatInOrderRepository;
    }

    public boolean isCompletedInOrderTable(UUID orderTableId){
        return !eatInOrderRepository.existsByOrderTableIdAndStatusNot(orderTableId, OrderStatus.COMPLETED);
    }
}
