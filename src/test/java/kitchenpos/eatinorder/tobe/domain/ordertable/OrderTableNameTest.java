package kitchenpos.eatinorder.tobe.domain.ordertable;

import kitchenpos.exception.IllegalNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTableNameTest {

    @DisplayName("이름이 같은 경우 동일하다.")
    @ParameterizedTest
    @ValueSource(strings = {"테스트"})
    void success(String input) {
        final var name1 = OrderTableName.of(input);
        final var name2 = OrderTableName.of(input);

        assertThat(name1).isEqualTo(name2);
    }

    @DisplayName("주문테이블 이름이 빈값일 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void fail(String input) {;

        assertThrows(IllegalNameException.class, () -> OrderTableName.of(input));
    }
}
