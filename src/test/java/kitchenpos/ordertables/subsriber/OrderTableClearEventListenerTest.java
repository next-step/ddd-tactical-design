package kitchenpos.ordertables.subsriber;

import kitchenpos.eatinorders.application.EatInOrderService;
import kitchenpos.eatinorders.application.InMemoryEatInOrderRepository;
import kitchenpos.eatinorders.application.OrderLinePolicy;
import kitchenpos.eatinorders.application.OrderTableStatusLoader;
import kitchenpos.eatinorders.application.service.DefaultOrderLinePolicy;
import kitchenpos.eatinorders.application.service.DefaultOrderTableStatusLoader;
import kitchenpos.eatinorders.domain.EatInOrder;
import kitchenpos.eatinorders.domain.EatInOrderRepository;
import kitchenpos.eatinorders.domain.EatInOrderStatus;
import kitchenpos.eatinorders.dto.EatInOrderResponse;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.ordertables.application.EatInOrderStatusLoader;
import kitchenpos.ordertables.application.InMemoryOrderTableRepository;
import kitchenpos.ordertables.application.OrderTableService;
import kitchenpos.ordertables.application.service.DefaultEatInOrderStatusLoader;
import kitchenpos.ordertables.domain.OrderTable;
import kitchenpos.ordertables.domain.OrderTableRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static kitchenpos.eatinorders.fixture.EatInOrderFixture.order;
import static kitchenpos.ordertables.fixture.OrderTableFixture.orderTable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        InMemoryOrderTableRepository.class,
        OrderTableClearEventListener.class,
        InMemoryEatInOrderRepository.class,
        EatInOrderService.class,
        OrderTableService.class,
        DefaultEatInOrderStatusLoader.class,
        DefaultOrderLinePolicy.class,
        InMemoryMenuRepository.class,
        DefaultOrderTableStatusLoader.class
})
@DisplayName("테이블 치우기 이벤트 발행")
class OrderTableClearEventListenerTest {

    @Autowired
    OrderTableRepository orderTableRepository;

    @Autowired
    OrderTableClearEventListener orderTableClearEventListener;

    @Autowired
    EatInOrderRepository eatInOrderRepository;

    @Autowired
    EatInOrderService eatInOrderService;

    @Autowired
    OrderTableService orderTableService;

    @Autowired
    EatInOrderStatusLoader eatInOrderStatusLoader;

    @Autowired
    OrderLinePolicy menuPriceLoader;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    OrderTableStatusLoader orderTableStatusLoader;

    @DisplayName("주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.")
    @Test
    void completeEatInOrder() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        final EatInOrder expected = eatInOrderRepository.save(order(EatInOrderStatus.SERVED, orderTable));
        final EatInOrderResponse actual = eatInOrderService.complete(expected.getId());
        assertAll(
                () -> assertThat(actual.getEatInOrderStatus()).isEqualTo(EatInOrderStatus.COMPLETED),
                () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().isOccupied()).isFalse(),
                () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().getNumberOfGuestValue()).isEqualTo(0)
        );
    }

    @DisplayName("완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.")
    @Test
    void completeNotTable() {
        final OrderTable orderTable = orderTableRepository.save(orderTable(true, 4));
        eatInOrderRepository.save(order(EatInOrderStatus.ACCEPTED, orderTable));
        final EatInOrder expected = eatInOrderRepository.save(order(EatInOrderStatus.SERVED, orderTable));
        final EatInOrderResponse actual = eatInOrderService.complete(expected.getId());
        assertAll(
                () -> assertThat(actual.getEatInOrderStatus()).isEqualTo(EatInOrderStatus.COMPLETED),
                () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().isOccupied()).isTrue(),
                () -> assertThat(orderTableRepository.findById(orderTable.getId()).get().getNumberOfGuestValue()).isEqualTo(4)
        );
    }
}
