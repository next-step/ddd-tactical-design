package kitchenpos.eatinorders.domain.tobe.domain;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.tobe.domain.vo.Guests;
import kitchenpos.eatinorders.dto.EatInOrderResponse;
import kitchenpos.eatinorders.dto.OrderStatusChangeRequest;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class EatInOrderCompleteService {
    private final EatInOrderRepository orderRepository;
    private final TobeOrderTableRepository orderTableRepository;


    public EatInOrderCompleteService(EatInOrderRepository orderRepository, TobeOrderTableRepository orderTableRepository) {
        this.orderRepository = orderRepository;
        this.orderTableRepository = orderTableRepository;
    }

    public EatInOrderResponse complete(OrderStatusChangeRequest request) {
        EatInOrder order = orderRepository.findById(request.getOrderId())
                .orElseThrow(NoSuchElementException::new)
                .complete();
        TobeOrderTable table = orderTableRepository.findById(order.getOrderTableId())
                .orElseThrow(NoSuchElementException::new);
        if (!orderRepository.existsByOrderTableAndStatusNot(table, OrderStatus.COMPLETED)) {
            table.changeNumberOfGuests(new Guests(0));
            table.clear();
        }
        return new EatInOrderResponse(order);
    }
}
