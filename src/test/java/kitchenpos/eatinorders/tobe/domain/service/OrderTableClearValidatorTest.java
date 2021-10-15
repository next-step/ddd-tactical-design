package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.commons.tobe.domain.service.Validator;
import kitchenpos.eatinorders.tobe.domain.model.*;
import kitchenpos.eatinorders.tobe.domain.repository.InMemoryOrderRepository;
import kitchenpos.eatinorders.tobe.domain.repository.OrderRepository;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kitchenpos.eatinorders.tobe.domain.fixture.OrderFixture.ORDER_WITH_TABLE;
import static kitchenpos.eatinorders.tobe.domain.fixture.OrderTableFixture.DEFAULT_ORDER_TABLE;
import static org.assertj.core.api.Assertions.*;

class OrderTableClearValidatorTest {

    private OrderRepository orderRepository;

    private Validator<OrderTable> orderTableClearValidator;

    @BeforeEach
    void setUp() {
        orderRepository = new InMemoryOrderRepository();
        orderTableClearValidator = new OrderTableClearValidator(orderRepository);
    }

    @DisplayName("주문 테이블에 계산 완료되지 않은 주문이 있으면 IllegalStateException을 던진다.")
    @Test
    void 빈_테이블_설정_실패() {
        final OrderTable orderTable = DEFAULT_ORDER_TABLE();
        orderRepository.save(ORDER_WITH_TABLE(orderTable));

        final ThrowableAssert.ThrowingCallable when = () -> orderTable.clear(orderTableClearValidator);

        assertThatIllegalStateException().isThrownBy(when)
                .withMessage("계산 완료되지 않은 주문이 있습니다.");
    }

    @DisplayName("주문 테이블에 계산 완료되지 않은 주문이 없으면 IllegalStateException을 던지지 않는다.")
    @Test
    void 빈_테이블_설정_성공() {
        final OrderTable orderTable = DEFAULT_ORDER_TABLE();
        final Order order = orderRepository.save(ORDER_WITH_TABLE(orderTable));
        order.accept();
        order.serve();
        order.complete(dummy -> {
        });

        try {
            orderTable.clear(orderTableClearValidator);
        } catch (IllegalStateException e) {
            fail("IllegalStateException을 던지지 않아야 한다.");
        }
    }
}
