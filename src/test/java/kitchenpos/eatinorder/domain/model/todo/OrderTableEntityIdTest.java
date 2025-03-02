package kitchenpos.eatinorder.domain.model.todo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderTableEntityIdTest {
    @DisplayName("OrderTableId를 생성한다.")
    @Test
    void create() {
        // given
        final UUID id = UUID.randomUUID();

        // when
        final OrderTableId orderTableId = OrderTableId.of(id);

        // then
        assertAll(
                () -> assertThat(orderTableId).isNotNull(),
                () -> assertThat(orderTableId.value()).isEqualTo(id)
        );
    }

    @DisplayName("OrderTableId를 생성할 때 id가 null인 경우 예외를 던진다.")
    @Test
    void createWithNull() {
        // given
        final UUID id = null;

        // when
        final Throwable thrown = catchThrowable(() -> OrderTableId.of(id));

        // then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 테이블 ID를 입력하세요.");
    }
}