package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Assertions.assertAll;

import kitchenpos.eatinorders.TobeFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class OrderTableTest {
    @DisplayName("성공 테스트")
    @Nested
    class SuccessTest {
        @DisplayName("유효한 인자인 경우 정상적으로 객체가 생성된다.")
        @Test
        void create() {
            // given
            String name = "테이블";

            // when
            OrderTable orderTable = new OrderTable(name);

            // then
            assertAll(
                () -> assertThat(orderTable.getId()).isNotNull(),
                () -> assertThat(orderTable.getName()).isEqualTo(name),
                () -> assertThat(orderTable.getNumberOfGuests()).isZero(),
                () -> assertThat(orderTable.isOccupied()).isFalse()
            );
        }

        @Test
        void sit() {
            // given
            OrderTable orderTable = TobeFixtures.orderTable();

            // when
            orderTable.sit();

            // then
            assertThat(orderTable.isOccupied()).isTrue();
        }

        @Test
        void clear() {
            // given
            OrderTable orderTable = TobeFixtures.orderTable();
            orderTable.sit();
            orderTable.changeNumberOfGuests(10);

            // when
            orderTable.clear();

            // then
            assertAll(
                () -> assertThat(orderTable.getNumberOfGuests()).isZero(),
                () -> assertThat(orderTable.isOccupied()).isFalse()
            );
        }

        @Test
        void changeNumberOfGuests() {
            // given
            OrderTable orderTable = TobeFixtures.orderTable();
            orderTable.sit();

            int numberOfGuests = 10;

            // when
            orderTable.changeNumberOfGuests(numberOfGuests);

            // then
            assertThat(orderTable.getNumberOfGuests()).isEqualTo(numberOfGuests);
        }
    }

    @DisplayName("실패 테스트")
    @Nested
    public class FailTest {
        @DisplayName("이름이 null 또는 빈 문자열 일 경우 예외가 발생한다.")
        @ParameterizedTest
        @NullAndEmptySource
        void nullAndEmptyName(String name) {
            assertThatIllegalArgumentException().isThrownBy(() -> new OrderTable(name));
        }

        @DisplayName("방문 손님수가 0보다 작을 경우 예외가 발생한다.")
        @Test
        void changeNumberOfGuests1() {
            // given
            OrderTable orderTable = TobeFixtures.orderTable();
            orderTable.sit();

            int numberOfGuests = -1;

            // when
            assertThatIllegalArgumentException().isThrownBy(
                () -> orderTable.changeNumberOfGuests(numberOfGuests)
            );
        }

        @DisplayName("빈 테이블일때 손님수를 변경할 경우 예외가 발생한다.")
        @Test
        void changeNumberOfGuests2() {
            // given
            OrderTable orderTable = TobeFixtures.orderTable();

            int numberOfGuests = 10;

            // when
            assertThatIllegalStateException().isThrownBy(
                () -> orderTable.changeNumberOfGuests(numberOfGuests)
            );
        }
    }
}
