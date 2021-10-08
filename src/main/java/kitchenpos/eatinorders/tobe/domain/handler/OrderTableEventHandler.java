package kitchenpos.eatinorders.tobe.domain.handler;

import java.util.NoSuchElementException;
import kitchenpos.eatinorders.tobe.domain.interfaces.event.OrderTableEventListener;
import kitchenpos.eatinorders.tobe.domain.model.OrderTable;
import kitchenpos.eatinorders.tobe.domain.model.TableCleanedEvent;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.service.OrderTableDomainService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class OrderTableEventHandler implements OrderTableEventListener {

    private final OrderTableRepository orderTableRepository;
    private final OrderTableDomainService orderTableDomainService;

    public OrderTableEventHandler(OrderTableRepository orderTableRepository, OrderTableDomainService orderTableDomainService) {
        this.orderTableRepository = orderTableRepository;
        this.orderTableDomainService = orderTableDomainService;
    }

    @TransactionalEventListener
    @Override
    public void handleOrderTableCleanedEvent(TableCleanedEvent event) {
        final OrderTable orderTable = orderTableRepository.findById(event.getOrderTableId())
                .orElseThrow(NoSuchElementException::new);

        orderTable.clear(orderTableDomainService);
    }
}
