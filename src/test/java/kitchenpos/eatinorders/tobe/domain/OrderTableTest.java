package kitchenpos.eatinorders.tobe.domain;

import static kitchenpos.eatinorders.tobe.OrderFixtures.numberOfGuests;
import static kitchenpos.eatinorders.tobe.OrderFixtures.orderTable;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import kitchenpos.eatinorders.tobe.domain.vo.NumberOfGuests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class OrderTableTest {

    private OrderTable table;

    @BeforeEach
    void setUp() {
        table = orderTable("테이블1");
    }

    @DisplayName("주문 테이블을 생성할 수 있다.")
    @Test
    void create() {
        assertThat(table).isNotNull();
        assertAll(
                () -> assertThat(table.getId()).isNotNull(),
                () -> assertThat(table.getNumberOfGuests()).isEqualTo(NumberOfGuests.zero()),
                () -> assertThat(table.isOccupied()).isFalse()
        );
    }

    @DisplayName("테이블을 배정한다.")
    @Test
    void sit() {
        table.sit();

        assertThat(table.isOccupied())
                .isTrue();
    }

    @DisplayName("빈 테이블로 지정한다.")
    @Test
    void clear() {
        table.clear();

        assertAll(
                () -> assertThat(table.isOccupied()).isFalse(),
                () -> assertThat(table.getNumberOfGuests()).isEqualTo(NumberOfGuests.zero())
        );
    }

    @DisplayName("손님 수 변경")
    @Nested
    class ChangeNumberOfGuests {


        @DisplayName("손님 수를 변경한다.")
        @Test
        void success() {
            table.sit();

            table.changeNumberOfGuests(numberOfGuests(1));

            assertThat(table.getNumberOfGuests())
                    .isEqualTo(numberOfGuests(1));
        }

        @DisplayName("배정된 테이블만 손님 수를 변경할 수 있다.")
        @Test
        void error() {
            assertThatExceptionOfType(IllegalStateException.class)
                    .isThrownBy(() -> table.changeNumberOfGuests(numberOfGuests(1)));
        }
    }
}
