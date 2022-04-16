package kitchenpos.eatinorders.tobe.domain.ordertable;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.UUID;
import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.DisplayedNamePolicy;
import kitchenpos.common.domain.FakeDisplayedNamePolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("orderTable 은")
class OrderTableTest {

    private static final DisplayedNamePolicy POLICY = new FakeDisplayedNamePolicy();
    private final DisplayedName name = new DisplayedName("test", POLICY);

    @DisplayName("등록할 수 있다")
    @Nested
    class 등록할_수_있다 {

        @DisplayName("이름이 비어있다면 등록할 수 없다.")
        @ParameterizedTest(name = "{0}인 경우")
        @NullAndEmptySource
        void 이름이_비어있다면_등록할_수_없다(String value) {
            assertThatIllegalArgumentException()
                .isThrownBy(() -> new OrderTable(UUID.randomUUID(), new DisplayedName(value, POLICY)));
        }

        @DisplayName("이름이 비어있지 않다면 등록할 수 있다.")
        @Test
        void 이름이_비어있지_않다면_등록할_수_있다() {
            assertDoesNotThrow(() -> new OrderTable(UUID.randomUUID(), new DisplayedName("test", POLICY)));
        }

        @DisplayName("비속어가 포함되어 있다면 등록할 수 없다")
        @ParameterizedTest
        @ValueSource(strings = {"비속어", "욕설"})
        void 비속어가_포함되어_있다면_등록할_수_없다(String value) {
            assertThatIllegalArgumentException()
                .isThrownBy(() -> new OrderTable(UUID.randomUUID(), new DisplayedName(value, POLICY)));
        }
    }

    @DisplayName("앉을 수 있다.")
    @Nested
    class 앉을_수_있다 {

        @DisplayName("비어있지 않다면 앉을 수 없다")
        @Test
        void 비어있지_않다면_앉을_수_없다() {
            final OrderTable orderTable = new OrderTable(new OrderTableId(UUID.randomUUID()), name, 3, false);

            assertThatIllegalStateException()
                .isThrownBy(() -> orderTable.sit());
        }

        @DisplayName("비어있다면 앉을 수 있다.")
        @Test
        void 비어있다면_앉을_수_있다() {
            final OrderTable orderTable = new OrderTable(UUID.randomUUID(), name);

            assertDoesNotThrow(() -> orderTable.sit());
        }
    }

    @DisplayName("손님의 수를 변경할 수 있다.")
    @Nested
    class 손님의_수를_변경할_수_있다 {

        @DisplayName("비어있다면 변경할 수 없다.")
        @Test
        void 비어있다면_변경할_수_없다() {
            final OrderTable orderTable = new OrderTable(UUID.randomUUID(), name);

            assertThatIllegalStateException()
                .isThrownBy(() -> orderTable.changeNumberOfGuests(1));
        }

        @DisplayName("비어있지 않지만 변경할 손님의 수가 0 미만인 경우 변경할 수 없다.")
        @Test
        void 비어있지_않지만_변경할_손님의_수가_0미만인_경우_변경할_수_없다() {
            final OrderTable orderTable = new OrderTable(new OrderTableId(UUID.randomUUID()), name, 3, false);

            assertThatIllegalArgumentException()
                .isThrownBy(() -> orderTable.changeNumberOfGuests(-1));
        }

        @DisplayName("비어있지 않고 변경할 손님의 수가 0 이상인 경우 변경할 수 있다.")
        @ParameterizedTest(name = "{0} 인 경우")
        @ValueSource(ints = {0, 1})
        void 비어있지_않고_변경할_손님의_수가_0이상인_경우_변경할_수_있다(int numberOfGuests) {
            final OrderTable orderTable = new OrderTable(new OrderTableId(UUID.randomUUID()), name, 3, false);

            assertDoesNotThrow(() -> orderTable.changeNumberOfGuests(numberOfGuests));
        }
    }
}
