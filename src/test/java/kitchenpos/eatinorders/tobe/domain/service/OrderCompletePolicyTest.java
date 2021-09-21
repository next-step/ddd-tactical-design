package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.commons.tobe.domain.model.DisplayedName;
import kitchenpos.commons.tobe.domain.model.Price;
import kitchenpos.commons.tobe.domain.service.Policy;
import kitchenpos.eatinorders.tobe.domain.model.*;
import kitchenpos.eatinorders.tobe.domain.model.orderstatus.Served;
import kitchenpos.eatinorders.tobe.domain.repository.InMemoryOrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

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
        final OrderTable orderTable = orderTableRepository.save(new OrderTable(
                UUID.randomUUID(),
                new DisplayedName("1번 테이블"),
                new NumberOfGuests(0L)
        ));
        final OrderLineItem orderLineItem = new OrderLineItem(
                UUID.randomUUID(),
                UUID.randomUUID(),
                new Price(BigDecimal.valueOf(16_000L)),
                1L
        );
        final OrderLineItems orderLineItems = new OrderLineItems(Collections.singletonList(orderLineItem));
        final Order order = new Order(UUID.randomUUID(), orderTable.getId(), new Served(), orderLineItems, dummy -> {
        });

        orderTable.sit();
        orderTable.changeNumberOfGuests(new NumberOfGuests(1L));
        order.proceed(orderCompletePolicy);

        assertAll(
                () -> assertThat(orderTable.getNumberOfGuests()).isZero(),
                () -> assertThat(orderTable.isEmpty()).isTrue()
        );
    }
}
