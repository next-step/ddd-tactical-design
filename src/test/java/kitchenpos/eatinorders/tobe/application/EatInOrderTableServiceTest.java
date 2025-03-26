package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.orderTable.EatInOrderTableRepository;
import kitchenpos.eatinorders.tobe.ui.dto.CreateEatInOrderTableRequest;
import kitchenpos.eatinorders.tobe.ui.dto.CreateEatInOrderTableResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EatInOrderTableServiceTest {

    private EatInOrderTableService eatInOrderTableService;

    private EatInOrderTableRepository eatInOrderTableRepository;

    @BeforeEach
    void setUp() {
        eatInOrderTableRepository = new InMemoryEatInOrderTableRepository();
        eatInOrderTableService = new EatInOrderTableService(eatInOrderTableRepository);
    }

    @Test
    void 주문_테이블을_등록하면_손님_수는_0명이고_사용_가능_상태여야_한다() {
        // given
        final CreateEatInOrderTableRequest expected = new CreateEatInOrderTableRequest("테이블명");

        // when
        final CreateEatInOrderTableResponse actual = eatInOrderTableService.create(expected);

        // then
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.name()).isEqualTo(expected.name()),
                () -> assertThat(actual.numberOfGuests()).isEqualTo(0),
                () -> assertThat(actual.occupied()).isEqualTo(false)
        );
    }

}
