package kitchenpos.eatinorders.tobe.domain.restaurant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

class RestaurantTableOccupiedTest {

    @DisplayName("가게 테이블 사용 상태를 설정 할 수 있다.")
    @Test
    void create() {
        // given
        boolean occupied = true;

        // when
        RestaurantTableOccupied restaurantTableOccupied = new RestaurantTableOccupied(occupied);

        // then
        assertThat(restaurantTableOccupied.getValue()).isEqualTo(occupied);
    }

    @DisplayName("가게 테이블 사용 상태를 null로 설정하면, 예외가 발생한다.")
    @ParameterizedTest
    @NullSource
    void createWithMinusNumber(Boolean occupied) {
        // when then
        assertThatThrownBy(() -> new RestaurantTableOccupied(occupied))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(RestaurantTableOccupied.ERROR_MESSAGE_VALUE_NULL);
    }
}
