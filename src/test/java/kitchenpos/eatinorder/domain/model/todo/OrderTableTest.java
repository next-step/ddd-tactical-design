package kitchenpos.eatinorder.domain.model.todo;

import kitchenpos.shared.domain.Profanities;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class OrderTableTest {
    @DisplayName("OrderTable을 생성한다")
    @Test
    void create() {
        // given
        final UUID id = UUID.randomUUID();
        final String name = "테이블";
        final Profanities profanities = nm -> false;

        // when
        final OrderTable orderTable = OrderTable.createEmptyTable(id, name, profanities);

        // then
        assertAll(
                () -> assertThat(orderTable).isNotNull(),
                () -> assertThat(orderTable.getId()).isEqualTo(id),
                () -> assertThat(orderTable.getName()).isEqualTo(name),
                () -> assertThat(orderTable.getNumberOfGuests()).isZero(),
                () -> assertThat(orderTable.isOccupied()).isFalse()
        );
    }

    @DisplayName("OrderTable를 처음 등록하면 EmptyTable 상태이다")
    @Test
    void createEmptyTable() {
        // given
        final String name = "테이블";
        final Profanities profanities = nm -> false;

        // when
        final OrderTable orderTable = OrderTable.createEmptyTable(UUID.randomUUID(), name, profanities);

        // then
        assertAll(
                () -> assertThat(orderTable).isNotNull(),
                () -> assertThat(orderTable.isEmpty()).isTrue()
        );
    }

    @DisplayName("OrderTable을 사용중 상태로 변경한다")
    @Test
    void sit() {
        // given
        final OrderTable orderTable = OrderTable.createEmptyTable(UUID.randomUUID(), "테이블", nm -> false);

        // when
        orderTable.sit();

        // then
        assertThat(orderTable.isOccupied()).isTrue();
    }

    @DisplayName("OrderTable을 공석 상태로 변경한다")
    @Test
    void clear() {
        // given
        final OrderTable orderTable = OrderTable.createEmptyTable(UUID.randomUUID(), "테이블", nm -> false);
        orderTable.sit();

        // when
        orderTable.clear();

        // then
        assertThat(orderTable.isEmpty()).isTrue();
    }
}