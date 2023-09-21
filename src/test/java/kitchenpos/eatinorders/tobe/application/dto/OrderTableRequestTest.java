package kitchenpos.eatinorders.tobe.application.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class OrderTableRequestTest {

    @DisplayName("이름이 빈 값일 수 없다")
    @ParameterizedTest
    @NullAndEmptySource
    void validateName(String name) {
        OrderTableRequest request = new OrderTableRequest(name, 1);
        Assertions.assertThatThrownBy(request::validate)
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("손님 수가 0 미만일 수 없다")
    @ParameterizedTest
    @ValueSource(ints = -1)
    void validateNumberOfGuests(int numberOfGuests) {
        OrderTableRequest request = new OrderTableRequest("테이블", numberOfGuests);
        Assertions.assertThatThrownBy(request::validate)
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
