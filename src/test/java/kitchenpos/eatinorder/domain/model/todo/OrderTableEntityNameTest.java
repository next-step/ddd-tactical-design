package kitchenpos.eatinorder.domain.model.todo;

import kitchenpos.shared.domain.Profanities;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderTableEntityNameTest {
    @DisplayName("OrderTableName을 생성한다.")
    @Test
    void create() {
        // given
        final String name = "테이블";
        final Profanities profanities = nm -> false;

        // when
        final OrderTableName orderTableName = OrderTableName.of(name, profanities);

        // then
        assertAll(
                () -> assertThat(orderTableName).isNotNull(),
                () -> assertThat(orderTableName.isSameAs(name)).isTrue()
        );
    }

    @DisplayName("OrderTableName을 생성할 때 이름이 비속어인 경우 예외를 던진다.")
    @Test
    void createWithProfanities() {
        // given
        final String name = "비속어";
        final Profanities profanities = nm -> true;

        // when
        final Throwable thrown = catchThrowable(() -> OrderTableName.of(name, profanities));

        // then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 테이블 이름에는 비속어를 사용할 수 없습니다. name: " + name);
    }

    @DisplayName("OrderTableName이 주어진 이름과 같은지 확인한다.")
    @Test
    void isSameAs() {
        // given
        final String name = "테이블";
        final Profanities profanities = nm -> false;

        // when
        final OrderTableName orderTableName = OrderTableName.of(name, profanities);

        // then
        assertThat(orderTableName.value()).isEqualTo(name);
    }

    @DisplayName("OrderTableName을 생성할 때 이름이 null이거나 공백일때 예외를 던진다.")
    @NullSource
    @ValueSource(strings = {"", " "})
    @ParameterizedTest
    void createWithBlankName(String name) {
        // given
        final Profanities profanities = nm -> false;

        // when
        final Throwable thrown = catchThrowable(() -> OrderTableName.of(name, profanities));

        // then
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 테이블 이름은 필수 입력값입니다.");
    }
}