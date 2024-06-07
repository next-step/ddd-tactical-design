package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.eatinorders.tobe.domain.entity.CompletedOrderEvent;
import kitchenpos.eatinorders.tobe.domain.entity.OrderTable;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.util.NoSuchElementException;

@Component
public class OrderTableModification {
    private final OrderTableRepository tableRepository;

    public OrderTableModification(OrderTableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @EventListener
    public void setEmptyTable(CompletedOrderEvent event) {
        System.out.println("주문 테이블 빈 테이블 설정");
        OrderTable table = tableRepository.findById(event.getOrderTableId())
                .orElseThrow(() -> new NoSuchElementException());
        table.clear();
    }
}
