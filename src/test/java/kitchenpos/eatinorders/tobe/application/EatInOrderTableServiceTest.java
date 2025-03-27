package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.exception.InvalidTableNameException;
import kitchenpos.eatinorders.tobe.domain.orderTable.EatInOrderTable;
import kitchenpos.eatinorders.tobe.domain.orderTable.EatInOrderTableRepository;
import kitchenpos.eatinorders.tobe.ui.dto.CreateEatInOrderTableRequest;
import kitchenpos.eatinorders.tobe.ui.dto.CreateEatInOrderTableResponse;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

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

    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    @ParameterizedTest(name = "{index}. 주문 테이블명: {0}")
    void 주문_테이블의_이름이_없으면_등록할_수_없다(final String invalidName) {
        // given
        final CreateEatInOrderTableRequest expected = new CreateEatInOrderTableRequest(invalidName);

        // when & then
        assertThatThrownBy(() -> eatInOrderTableService.create(expected))
                .isInstanceOf(InvalidTableNameException.class)
                .hasMessage("주문 테이블이 존재해야 합니다.");
    }

    @Test
    void 주문_테이블에_손님이_앉으면_사용중_상태로_변경된다() {
        // given
        final EatInOrderTable eatInOrderTable = sitEatInOrderTable("1번 테이블", 0, false);
        final EatInOrderTable expected = eatInOrderTableRepository.save(eatInOrderTable);

        // when
        EatInOrderTable actual = eatInOrderTableService.sit(expected.getId());

        // then
        assertThat(actual.getOccupied()).isTrue();
    }

    private EatInOrderTable sitEatInOrderTable(final String name, final int numberOfGuests, final boolean occupied) {
        EatInOrderTable eatInOrderTable = new EatInOrderTable(name, numberOfGuests, occupied);
        return eatInOrderTable;
    }
}
