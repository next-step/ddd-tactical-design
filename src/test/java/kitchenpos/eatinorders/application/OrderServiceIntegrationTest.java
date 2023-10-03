package kitchenpos.eatinorders.application;

import kitchenpos.common.domain.Price;
import kitchenpos.eatinorders.application.dto.EatInOrderLineItemRequest;
import kitchenpos.eatinorders.application.dto.EatInOrderRequest;
import kitchenpos.eatinorders.application.dto.EatInOrderResponse;
import kitchenpos.eatinorders.application.dto.OrderStatusResponse;
import kitchenpos.eatinorders.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.NewFixtures.orderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@RecordApplicationEvents
public class OrderServiceIntegrationTest {
    @Autowired
    private ApplicationEvents events;
    @Autowired
    private OrderService orderService;
    @Autowired
    private EatInOrderRepository eatInOrderRepository;
    @Autowired
    private EatInOrderTableRepository eatInOrderTableRepository;
    @Autowired
    private EatInMenuRepository eatInMenuRepository;
    private EatInOrder eatInOrder;
    private EatInOrderTable eatInOrderTable;
    private EatInMenu menu;

    @BeforeEach
    void setUp() {
        eatInOrderTable = eatInOrderTableRepository.save(orderTable(true, 4));
        menu = eatInMenuRepository.save(EatInMenu.create(UUID.randomUUID(), Price.of(BigDecimal.valueOf(10_000)), true));
        EatInOrderResponse eatInOrderResponse = orderService.create(EatInOrderRequest.create(
                eatInOrderTable.getId(),
                List.of(EatInOrderLineItemRequest.create(1, menu.getId(), BigDecimal.valueOf(10_000)))
        ));
        eatInOrder = eatInOrderRepository.findById(eatInOrderResponse.getId()).get();
        eatInOrder.accept();
        eatInOrder.serve();
        eatInOrderRepository.save(eatInOrder);
    }

    @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
    @Transactional
    @Test
    void completeEatInOrder() {
        final OrderStatusResponse actual = orderService.complete(eatInOrder.getId());
        assertAll(
                () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED),
                () -> assertThat(eatInOrderTableRepository.findById(eatInOrderTable.getId()).get().isOccupied()).isFalse(),
                () -> assertThat(eatInOrderTableRepository.findById(eatInOrderTable.getId()).get().getNumberOfGuests()).isEqualTo(NumberOfGuests.ZERO)
        );
    }

    @DisplayName("완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.")
    @Test
    void completeNotTable() {
        EatInOrderResponse notCompletedOrder = orderService.create(EatInOrderRequest.create(
                eatInOrderTable.getId(),
                List.of(EatInOrderLineItemRequest.create(1, menu.getId(), BigDecimal.valueOf(10_000)))
        ));

        final OrderStatusResponse actual = orderService.complete(eatInOrder.getId());
        assertAll(
                () -> assertThat(actual.getStatus()).isEqualTo(OrderStatus.COMPLETED),
                () -> assertThat(eatInOrderTableRepository.findById(eatInOrderTable.getId()).get().isOccupied()).isTrue(),
                () -> assertThat(eatInOrderTableRepository.findById(eatInOrderTable.getId()).get().getNumberOfGuests()).isEqualTo(NumberOfGuests.create(4))
        );
    }
}
