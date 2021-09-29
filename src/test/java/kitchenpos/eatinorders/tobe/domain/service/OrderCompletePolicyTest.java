package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.commons.tobe.domain.service.Policy;
import kitchenpos.eatinorders.tobe.domain.model.*;
import kitchenpos.eatinorders.tobe.domain.repository.InMemoryOrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static kitchenpos.eatinorders.tobe.domain.fixture.OrderLineItemFixture.DEFAULT_ORDER_LINE_ITEM;
import static kitchenpos.eatinorders.tobe.domain.fixture.OrderTableFixture.DEFAULT_ORDER_TABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderCompletePolicyTest {

    private OrderTableRepository orderTableRepository;

    private Policy<Order> orderCompletePolicy;

    @BeforeEach
    void setUp() {
        orderTableRepository = new InMemoryOrderTableRepository();
        orderCompletePolicy = new OrderCompletePolicy(orderTableRepository, dummy -> {
        });
    }

    @DisplayName("주문이 계산 완료되면, 주문 테이블의 방문한 손님 수를 0으로 변경하고, 빈 테이블로 설정한다")
    @Test
    void 주문_계산_완료_정책_실행_성공() {
        final OrderTable orderTable = orderTableRepository.save(DEFAULT_ORDER_TABLE());
        final OrderLineItem orderLineItem = DEFAULT_ORDER_LINE_ITEM();
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = Order.create(UUID.randomUUID(), orderTable.getId(), orderLineItems, dummy -> {
        });

        orderTable.sit();
        orderTable.changeNumberOfGuests(new NumberOfGuests(1L));
        order.accept();
        order.serve();
        order.complete(dummy -> {
        });
        orderCompletePolicy.enforce(order);

        assertAll(
                () -> assertThat(orderTable.getNumberOfGuests()).isZero(),
                () -> assertThat(orderTable.isEmpty()).isTrue()
        );
    }
}
