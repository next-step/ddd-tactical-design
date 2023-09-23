package kitchenpos.eatinorders.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kitchenpos.eatinorders.application.OrderTableService;
import kitchenpos.eatinorders.application.dto.ChangeNumberOfGuestsRequest;
import kitchenpos.eatinorders.application.dto.CreateOrderTableRequest;
import kitchenpos.eatinorders.domain.OrderRepository;
import kitchenpos.eatinorders.domain.OrderTable;
import kitchenpos.eatinorders.domain.OrderTableRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OrderTableServiceTest {

    @Autowired
    private OrderTableService orderTableService;

    @Mock
    private OrderRepository orderRepository;

    @Autowired
    private OrderTableRepository orderTableRepository;

    @BeforeEach
    void init() {
        orderTableService = new OrderTableService(orderTableRepository, orderRepository);
    }

    @Test
    void 주문테이블_생성_실패__이름이_null() {
        CreateOrderTableRequest request = OrderTableRequestFixture.builder()
                .name(null)
                .buildCreateRequest();

        assertThatThrownBy(() -> orderTableService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문테이블_생성_실패__이름이_비어있음() {
        CreateOrderTableRequest request = OrderTableRequestFixture.builder()
                .name("")
                .buildCreateRequest();

        assertThatThrownBy(() -> orderTableService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문테이블_정리_실패__해당_주문테이블에_완료되지_않은_주문이_존재() {
        OrderTable orderTable = orderTableService.create(OrderTableRequestFixture.builder().buildCreateRequest());
        when(orderRepository.existsByOrderTableAndStatusNot(any(), any())).thenReturn(true);

        assertThatThrownBy(() -> orderTableService.clear(orderTable.getId()))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 주문테이블_손님수_변경_실패__손님수가_음수() {
        OrderTable orderTable = orderTableService.create(OrderTableRequestFixture.builder().buildCreateRequest());
        orderTable.use();
        orderTableRepository.save(orderTable);
        ChangeNumberOfGuestsRequest request = OrderTableRequestFixture.builder()
                .numberOfGuests(-1)
                .buildChangeRequest();

        assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTable.getId(), request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주문테이블_손님수_변경_실패__주문테이블이_착석상태가_아님() {
        CreateOrderTableRequest createRequest = OrderTableRequestFixture.builder().buildCreateRequest();
        OrderTable orderTable = orderTableService.create(createRequest);
        ChangeNumberOfGuestsRequest request = OrderTableRequestFixture.builder().buildChangeRequest();

        assertThatThrownBy(() -> orderTableService.changeNumberOfGuests(orderTable.getId(), request))
                .isInstanceOf(IllegalStateException.class);
    }
}
