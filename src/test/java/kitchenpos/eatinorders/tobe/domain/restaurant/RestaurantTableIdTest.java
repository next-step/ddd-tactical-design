package kitchenpos.eatinorders.tobe.domain.restaurant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class RestaurantTableIdTest {

    @DisplayName("UUID로 가게 테이블 ID를 생성할 수 있다.")
    @Test
    void create() {
        // given
        UUID id = UUID.randomUUID();

        // when
        RestaurantTableId restaurantTableId = new RestaurantTableId(id);

        // then
        assertThat(restaurantTableId.getValue()).isEqualTo(id);
    }

    @DisplayName("가게 테이블 ID 값이 올바르지 않으면, 예외가 발생한다.")
    @ParameterizedTest
    @NullSource
    void createWithNull(UUID value) {
        // when then
        assertThatThrownBy(() -> new RestaurantTableId(value))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(RestaurantTableId.ERROR_MESSAGE_VALUE_NULL);
    }
}
