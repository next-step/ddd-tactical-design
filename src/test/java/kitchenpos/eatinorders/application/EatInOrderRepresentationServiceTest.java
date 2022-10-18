package kitchenpos.eatinorders.application;

import static kitchenpos.eatinorders.EatInOrderFixtures.eatInOrder;
import static kitchenpos.eatinordertables.EatInOrderTableFixtures.eatInOrderTable;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kitchenpos.eatinorders.domain.EatInOrderRepository;
import kitchenpos.eatinorders.domain.EatInOrderStatus;
import kitchenpos.eatinorders.domain.InMemoryEatInOrderRepository;
import kitchenpos.eatinorders.ui.response.EatInOrderResponse;
import kitchenpos.eatinordertables.domain.EatInOrderTable;
import kitchenpos.eatinordertables.domain.EatInOrderTableRepository;
import kitchenpos.eatinordertables.domain.InMemoryEatInOrderTableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EatInOrderRepresentationServiceTest {

    private EatInOrderRepresentationService eatInOrderRepresentationService;
    private EatInOrderRepository eatInOrderRepository;
    private EatInOrderTableRepository eatInOrderTableRepository;

    @BeforeEach
    void setUp() {
        eatInOrderRepository = new InMemoryEatInOrderRepository();
        eatInOrderTableRepository = new InMemoryEatInOrderTableRepository();
        eatInOrderRepresentationService = new EatInOrderRepresentationService(eatInOrderRepository);
    }

    @DisplayName("주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.save(eatInOrderTable(
            true,
            4
        ));
        eatInOrderRepository.save(eatInOrder(EatInOrderStatus.SERVED, eatInOrderTable.getId()));
        eatInOrderRepository.save(eatInOrder(EatInOrderStatus.COMPLETED, eatInOrderTable.getId()));
        final List<EatInOrderResponse> responses = eatInOrderRepresentationService.findAll();
        assertThat(responses).hasSize(2);
    }
}
