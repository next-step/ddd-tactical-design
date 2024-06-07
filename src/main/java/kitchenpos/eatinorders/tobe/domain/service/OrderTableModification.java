package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.eatinorders.tobe.domain.entity.CompletedOrderEvent;
import kitchenpos.eatinorders.tobe.domain.entity.OrderTable;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.util.NoSuchElementException;

@Component
public class OrderTableModification {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderTableModification.class);
    private final OrderTableRepository tableRepository;

    public OrderTableModification(OrderTableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @EventListener
    public void setEmptyTable(CompletedOrderEvent event) {
        LOGGER.info("주문이 완료 되어 빈 테이블 설정을 진행합니다.");
        OrderTable table = tableRepository.findById(event.getOrderTableId())
                .orElseThrow(() -> new NoSuchElementException());
        table.clear();
    }
}
