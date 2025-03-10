package kitchenpos.eatinorders.tobe.domain.eatinorder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class EatInOrderDateTimeTest {

    @DisplayName("매장 식사 주문 항목 날짜를 생성할 수 있다.")
    @Test
    void create() {
        // given
        LocalDateTime localDateTime = LocalDateTime.now();

        // when
        EatInOrderDateTime eatInOrderDateTime = new EatInOrderDateTime(localDateTime);

        // then
        assertThat(eatInOrderDateTime.getValue()).isEqualTo(localDateTime);
    }

    @DisplayName("날짜 없이 매장 식사 주문 항목 날짜를 생성 시, 예외가 발생한다.")
    @ParameterizedTest
    @NullSource
    void createWithNullName(LocalDateTime localDateTime) {
        // when then
        assertThatThrownBy(() -> new EatInOrderDateTime(localDateTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(EatInOrderDateTime.ERROR_MESSAGE_VALUE_NULL);
    }
}
