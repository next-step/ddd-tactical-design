package kitchenpos.eatinorders.domain;

import kitchenpos.eatinorders.EatInOrderFixture;
import kitchenpos.eatinorders.tobe.constant.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.entity.EatInOrder;
import kitchenpos.eatinorders.tobe.entity.OrderLineItem;
import kitchenpos.eatinorders.tobe.entity.OrderLineItems;
import kitchenpos.eatinorders.tobe.entity.OrderTable;
import kitchenpos.eatinorders.tobe.repository.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.repository.OrderTableRepository;
import kitchenpos.eatinorders.tobe.service.EatInOrderDomainService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("주문 도메인 서비스 테스트")
public class EatInOrderDomainServiceTest {
    private EatInOrderRepository orderRepository;
    private OrderTableRepository orderTableRepository;
    private EatInOrderDomainService eatInOrderDomainService;

    @BeforeEach
    void setUp() {
        orderRepository = new FakeEatInOrderRepository();
        orderTableRepository = new FakeOrderTableRepository();
        eatInOrderDomainService = new EatInOrderDomainService(orderRepository, orderTableRepository);
    }

    @Test
    @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
    void create_exception_emptyOrderTable() {
        OrderTable 빈_테이블 = EatInOrderFixture.emptyOrderTableOf("주문_테이블");
        orderTableRepository.save(빈_테이블);

        Assertions.assertThatThrownBy(
                () -> EatInOrderFixture.eatInOrderOf(
                        createDefaultOrderLineItems(),
                        빈_테이블.getId(),
                        eatInOrderDomainService)
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("주문 테이블에 있는 모든 주문이 완료되면 빈 테이블로 설정한다.")
    void complete_orderTable () {
        OrderTable 주문_테이블 = orderTableRepository.save(EatInOrderFixture.sitOrderTableOf("주문_테이블"));
        EatInOrder 매장_식사 = orderRepository.save(
                EatInOrderFixture.eatInOrderOf(createDefaultOrderLineItems(), 주문_테이블.getId())
        );
        statusAcceptToServe(매장_식사);

        매장_식사.complete(eatInOrderDomainService);

        assertAll(
                () -> Assertions.assertThat(주문_테이블.getNumberOfGuests()).isZero(),
                () -> Assertions.assertThat(주문_테이블.isNotOccupied()).isTrue()
        );
    }

    @Test
    @DisplayName("주문 테이블에 있는 모든 주문이 완료되지 않으면 빈 테이블로 설정되지 않는다.")
    void complete_orderTable_exception() {
        OrderTable 주문_테이블 = orderTableRepository.save(EatInOrderFixture.sitOrderTableOf("주문_테이블"));
        EatInOrder 매장_식사 = orderRepository.save(
                EatInOrderFixture.eatInOrderOf(createDefaultOrderLineItems(), 주문_테이블.getId())
        );
        EatInOrder 매장_식사2 = orderRepository.save(
                EatInOrderFixture.eatInOrderOf(createDefaultOrderLineItems(), 주문_테이블.getId())
        );
        statusAcceptToServe(매장_식사);

        매장_식사.complete(eatInOrderDomainService);

        Assertions.assertThat(주문_테이블.isOccupied()).isTrue();
    }

    private void statusAcceptToServe(EatInOrder order) {
        order.accept();
        order.serve();
    }

    private OrderLineItems createDefaultOrderLineItems() {
        kitchenpos.eatinorders.tobe.entity.OrderLineItem orderLineItem1 = EatInOrderFixture.orderLineItemOf(5, BigDecimal.valueOf(10_000));
        OrderLineItem orderLineItem2 = EatInOrderFixture.orderLineItemOf(5, BigDecimal.valueOf(10_000));
        return new OrderLineItems(List.of(orderLineItem1, orderLineItem2));
    }
}
