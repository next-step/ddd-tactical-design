package kitchenpos.eatinordertables.application;

import static kitchenpos.eatinordertables.EatInOrderTableFixtures.eatInOrderTable;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kitchenpos.eatinordertables.domain.EatInOrderTableRepository;
import kitchenpos.eatinordertables.domain.InMemoryEatInOrderTableRepository;
import kitchenpos.eatinordertables.ui.response.EatInOrderTableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EatInOrderTableRepresentationServiceTest {
    private EatInOrderTableRepository eatInOrderTableRepository;
    private EatInOrderTableRepresentationService eatInOrderTableRepresentationService;

    @BeforeEach
    void setUp() {
        eatInOrderTableRepository = new InMemoryEatInOrderTableRepository();
        eatInOrderTableRepresentationService = new EatInOrderTableRepresentationService(eatInOrderTableRepository);
    }

    @DisplayName("주문 테이블의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        eatInOrderTableRepository.save(eatInOrderTable());
        final List<EatInOrderTableResponse> responses = eatInOrderTableRepresentationService.findAll();
        assertThat(responses).hasSize(1);
    }
}
