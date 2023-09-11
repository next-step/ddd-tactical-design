package kitchenpos.eatinorders.tobe;

import kitchenpos.eatinorders.application.InMemoryOrderRepository;
import kitchenpos.eatinorders.application.InMemoryOrderTableRepository;
import kitchenpos.eatinorders.tobe.application.ToBeOrderTableService;
import kitchenpos.eatinorders.tobe.domain.*;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTableNameVOTest {
    private ToBeOrderTableRepository orderTableRepository;
    private ToBeOrderRepository orderRepository;
    private ToBeOrderTableService orderTableService;

    @BeforeEach
    void setUp() {
        orderTableRepository = new InMemoryOrderTableRepository();
        orderRepository = new InMemoryOrderRepository();
        orderTableService = new ToBeOrderTableService(orderTableRepository, orderRepository);

    }


    @DisplayName("주문 테이블의 이름이 올바르지 않으면 등록할 수 없다.")
    @EmptySource
    @ParameterizedTest
    void create(final String name) {
        assertThatThrownBy(() -> createOrderTableRequest(name))
                .isInstanceOf(IllegalArgumentException.class);
    }


    private ToBeOrderTable createOrderTableRequest(final String name) {
        final ToBeOrderTable orderTable = new ToBeOrderTable();
        ReflectionTestUtils.setField(orderTable,"name", name);

        return orderTable;
    }

}
