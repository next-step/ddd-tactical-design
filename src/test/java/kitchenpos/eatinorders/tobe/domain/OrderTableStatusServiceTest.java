package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.application.InMemoryOrderTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderTableStatusServiceTest {

    private OrderTableRepository orderTableRepository;
    private OrderTableStatusService orderTableStatusService;

    @BeforeEach
    void setUp() {
        this.orderTableRepository = new InMemoryOrderTableRepository();
        this.orderTableStatusService = new OrderTableStatusService(orderTableRepository);
    }

    @Test
    @DisplayName("테이블 비어 있는 상태 확인 성공")
    void successClear() {

        //given
        OrderTable orderTable = new OrderTable("9번");
        this.orderTableRepository.save(orderTable);

        //when
        boolean isEmpty = orderTableStatusService.isCleared(orderTable.getId());

        //then
        assertThat(isEmpty).isTrue();
    }

    @Test
    @DisplayName("테이블이 현재 사용중이다.")
    void isUsedTable() {

        //given
        OrderTable orderTable = new OrderTable("9번");
        this.orderTableRepository.save(orderTable);
        orderTable.sit();
        orderTable.changeNumberOfGuests(10);

        //when
        boolean isEmpty = orderTableStatusService.isCleared(orderTable.getId());

        //then
        assertThat(isEmpty).isFalse();
    }

}
