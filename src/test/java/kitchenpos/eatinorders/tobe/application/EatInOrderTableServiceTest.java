package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.exception.InvalidOccupiedException;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidTableNameException;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.orderTable.EatInOrderTable;
import kitchenpos.eatinorders.tobe.domain.orderTable.EatInOrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.orderTable.EmptyOrderTableOrders;
import kitchenpos.eatinorders.tobe.domain.orderTable.OrderTableOrders;
import kitchenpos.eatinorders.tobe.ui.dto.ChangeNumberOfGuestsRequest;
import kitchenpos.eatinorders.tobe.ui.dto.CreateEatInOrderTableRequest;
import kitchenpos.eatinorders.tobe.ui.dto.CreateEatInOrderTableResponse;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.assertj.core.api.AssertionsForClassTypes;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

class EatInOrderTableServiceTest {

    private EatInOrderTableService eatInOrderTableService;
    private EatInOrderRepository eatInOrderRepository;
    private EatInOrderTableRepository eatInOrderTableRepository;
    private OrderTableOrders orderTableOrders;


    @BeforeEach
    void setUp() {
        orderTableOrders = new EmptyOrderTableOrders();
        eatInOrderTableRepository = new InMemoryEatInOrderTableRepository();
        eatInOrderTableService = new EatInOrderTableService(eatInOrderRepository, eatInOrderTableRepository, orderTableOrders);
    }

    @Test
    void 주문_테이블을_등록하면_손님_수는_0명이고_사용_가능_상태여야_한다() {
        // given
        final CreateEatInOrderTableRequest expected = new CreateEatInOrderTableRequest("테이블명");

        // when
        final CreateEatInOrderTableResponse actual = eatInOrderTableService.create(expected);

        // then
        AssertionsForClassTypes.assertThat(actual).isNotNull();
        assertAll(
                () -> AssertionsForClassTypes.assertThat(actual.name()).isEqualTo(expected.name()),
                () -> AssertionsForClassTypes.assertThat(actual.numberOfGuests()).isEqualTo(0),
                () -> AssertionsForClassTypes.assertThat(actual.occupied()).isEqualTo(false)
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
        final EatInOrderTable eatInOrderTable = createEatInOrderTable("1번 테이블", 0, false);
        final EatInOrderTable expected = eatInOrderTableRepository.save(eatInOrderTable);

        // when
        final EatInOrderTable actual = eatInOrderTableService.sit(expected.getId());

        // then
        AssertionsForClassTypes.assertThat(actual.getOccupied()).isTrue();
    }

    @Test
    void 주문이_완료된_경우_주문_테이블을_정리할_수_있다() {
        // given
        final EatInOrderTable eatInOrderTable = createEatInOrderTable("1번 테이블", 4, true);
        final EatInOrderTable expected = eatInOrderTableRepository.save(eatInOrderTable);

        final EatInOrderTable actual = eatInOrderTableService.clear(expected.getId());

        // then
        assertAll(
                () -> Assertions.assertThat(actual.getNumberOfGuests()).isEqualTo(0),
                () -> Assertions.assertThat(actual.occupied()).isFalse()
        );
    }

    @Test
    void 주문_테이블의_손님_수를_변경할_수_있다() {
        // given
        final EatInOrderTable expected = eatInOrderTableRepository.save(createEatInOrderTable("1번 테이블", 4, true));
        final ChangeNumberOfGuestsRequest request = new ChangeNumberOfGuestsRequest(5);
        final EatInOrderTable actual = eatInOrderTableService.changeNumberOfGuests(expected.getId(), request);

        // when & then
        AssertionsForClassTypes.assertThat(actual.numberOfGuests()).isEqualTo(5);
    }

    @Test
    void 사용_중이_아닌_주문_테이블은_손님_수를_변경할_수_없다() {
        // given
        final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.save(createEatInOrderTable("1번 테이블", 4, false));
        final ChangeNumberOfGuestsRequest request = new ChangeNumberOfGuestsRequest(5);

        // when & then
        assertThatThrownBy(() ->
                eatInOrderTableService.changeNumberOfGuests(eatInOrderTable.getId(), request)
        ).isInstanceOf(InvalidOccupiedException.class)
                .hasMessage("손님 수를 변경하려면 테이블이 사용 중이어야 합니다.");
    }

    @Test
    void 모든_주문_테이블을_모두_조회할_수_있다() {
        // given
        final EatInOrderTable eatInOrderTableOne = eatInOrderTableRepository.save(createEatInOrderTable("1번 테이블", 4, true));
        final EatInOrderTable eatInOrderTableTwo = eatInOrderTableRepository.save(createEatInOrderTable("2번 테이블", 4, true));

        // when & then
        final List<EatInOrderTable> result = eatInOrderTableRepository.findAll();

        // then
        assertThat(result).hasSize(2)
                .extracting(EatInOrderTable::getId)
                .containsExactlyInAnyOrder(
                        eatInOrderTableOne.getId(),
                        eatInOrderTableTwo.getId()
                );
    }

    private EatInOrderTable createEatInOrderTable(final String name, final int numberOfGuests, final boolean occupied) {
        EatInOrderTable eatInOrderTable = new EatInOrderTable(name, numberOfGuests, occupied);
        return eatInOrderTable;
    }
}
