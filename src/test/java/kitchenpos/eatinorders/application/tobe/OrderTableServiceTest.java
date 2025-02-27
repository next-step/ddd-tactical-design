package kitchenpos.eatinorders.application.tobe;

import kitchenpos.common.vo.PositiveNumber;
import kitchenpos.eatinorders.infra.InMemoryOrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.OrderTableName;
import kitchenpos.eatinorders.tobe.domain.OrderTableRepository;
import kitchenpos.eatinorders.ui.dto.OrderTableCreateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderTableServiceTest {

    private OrderTableRepository orderTableRepository;
    private OrderTableService orderTableService;

    @BeforeEach
    void setup() {
        orderTableRepository = new InMemoryOrderTableRepository();
        orderTableService = new OrderTableService(orderTableRepository);
    }

    @DisplayName("주문 테이블을 생성한다")
    @Test
    void create() {
        OrderTableCreateResponse result = orderTableService.create("1번 테이블");

        assertAll(
                () -> assertThat(result.getName()).isEqualTo(new OrderTableName("1번 테이블")),
                () -> assertThat(result.getNumberOfGuests()).isEqualTo(PositiveNumber.ZERO),
                () -> assertThat(result.isOccupied()).isFalse()
        );
    }

}
