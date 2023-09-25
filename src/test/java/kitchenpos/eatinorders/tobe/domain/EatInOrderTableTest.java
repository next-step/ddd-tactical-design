package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static kitchenpos.eatinorders.exception.OrderTableExceptionMessage.NOT_OCCUPIED_GUESTS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("주문테이블 테스트")
class EatInOrderTableTest {

    @DisplayName("주문테이블 생성 성공")
    @Test
    void create() {
        UUID id = UUID.randomUUID();

        EatInOrderTable result = EatInOrderTable.create(id, OrderTableName.create("1번테이블"), NumberOfGuests.ZERO, true);

        assertThat(result).isEqualTo(EatInOrderTable.create(id, OrderTableName.create("1번테이블"), NumberOfGuests.ZERO, true));
    }


    @DisplayName("테이블 앉기 성공")
    @Test
    void sit() {
        EatInOrderTable eatInOrderTable = EatInOrderTable.create(UUID.randomUUID(), OrderTableName.create("1번테이블"), NumberOfGuests.create(10), false);
        eatInOrderTable.sit();

        assertThat(eatInOrderTable.isOccupied()).isTrue();
    }

    @DisplayName("테이블 청소 성공")
    @Test
    void clear() {
        EatInOrderTable eatInOrderTable = EatInOrderTable.create(UUID.randomUUID(), OrderTableName.create("1번테이블"), NumberOfGuests.create(10), true);
        eatInOrderTable.clear();

        assertThat(eatInOrderTable.isOccupied()).isFalse();
        assertThat(eatInOrderTable.getNumberOfGuestsValue()).isEqualTo(0);
    }

    @DisplayName("빈테이블의 고객수를 변경할 수 없다.")
    @Test
    void change_numberOfGuests_failed() {
        EatInOrderTable eatInOrderTable = EatInOrderTable.create(UUID.randomUUID(), OrderTableName.create("1번테이블"), NumberOfGuests.create(0), false);

        assertThatThrownBy( () -> eatInOrderTable.changeNumberOfGuests(NumberOfGuests.create(10)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(NOT_OCCUPIED_GUESTS);
    }

    @DisplayName("테이블 고객수 변경 성공")
    @Test
    void change_numberOfGuests() {
        EatInOrderTable eatInOrderTable = EatInOrderTable.create(UUID.randomUUID(), OrderTableName.create("1번테이블"), NumberOfGuests.create(4), true);

        eatInOrderTable.changeNumberOfGuests(NumberOfGuests.create(8));

        assertThat(eatInOrderTable.getNumberOfGuestsValue()).isEqualTo(8);
    }

}