package kitchenpos.eatinorders.tobe.domain.orderTable;


import kitchenpos.eatinorders.tobe.domain.exception.InvalidNumberOfGuestsException;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOccupiedException;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidTableNameException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("주문 테이블에 대한 테스트")
class EatInOrderTableTest {

    @ParameterizedTest(name = "{index}. 주문 테이블 이름: {0}")
    @NullAndEmptySource
    void 주문_테이블이_존재해야_한다(final String name) {
        // given & when & then
        assertThatThrownBy(() -> new EatInOrderTable(name, 0, false))
                .isInstanceOf(InvalidTableNameException.class)
                .hasMessage("주문 테이블이 존재해야 합니다.");
    }

    @ParameterizedTest(name = "{index}. 방문한 손님 수: {0}")
    @ValueSource(ints = {1, 10, 1000})
    void 주문_테이블에_있는_손님_수를_변경할_수_있다(final int numberOfGuests) {
        // given
        final EatInOrderTable orderTable = new EatInOrderTable("1번", 0, true);

        // when
        orderTable.changeNumberOfGuests(numberOfGuests);

        // then
        assertThat(orderTable.numberOfGuests()).isEqualTo(numberOfGuests);
    }

    @ParameterizedTest(name = "{index}. 테이블이 사용 중인 아닌 상태에서 손님 수 변경 시도: {0}")
    @ValueSource(ints = {1, 3, 5})
    void 사용_중이_아닌_테이블의_손님_수는_변경할_수_없다(final int numberOfGuests) {
        // given
        final EatInOrderTable orderTable = new EatInOrderTable("1번", 0, false);

        // when & then
        assertThatThrownBy(() -> orderTable.changeNumberOfGuests(numberOfGuests))
                .isInstanceOf(InvalidOccupiedException.class)
                .hasMessage("손님 수를 변경하려면 테이블이 사용 중이어야 합니다.");
    }

    @ParameterizedTest(name = "{index}. 방문한 손님 수: {0}")
    @ValueSource(ints = {-1, -10, -100})
    void 방문한_손님_수가_0명_미만이면_사용중인_테이블의_손님_수를_변경할_수_없다(final int numberOfGuests) {
        // given
        final EatInOrderTable orderTable = new EatInOrderTable("1번", 0, true);

        // when & then
        assertThatThrownBy(() -> orderTable.changeNumberOfGuests(numberOfGuests))
                .isInstanceOf(InvalidNumberOfGuestsException.class)
                .hasMessage("방문한 손님 수가 0명 이상이어야 합니다.");
    }
}
