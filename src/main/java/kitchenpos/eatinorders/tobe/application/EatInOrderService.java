package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.OrderTableStatusService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EatInOrderService {

    private final EatInOrderRepository eatInOrderRepository;
    private final OrderTableStatusService orderTableStatusService;

    public EatInOrderService(EatInOrderRepository eatInOrderRepository, OrderTableStatusService orderTableStatusService) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.orderTableStatusService = orderTableStatusService;
    }

    public void create(final OrderLineItems orderLineItems, final UUID orderTableId){
        orderTableStatusService.isCleared(orderTableId);
        EatInOrder eatInOrder = new EatInOrder(orderLineItems, orderTableId);
        eatInOrderRepository.save(eatInOrder);
    }
}
