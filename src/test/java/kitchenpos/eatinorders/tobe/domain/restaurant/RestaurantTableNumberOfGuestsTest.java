package kitchenpos.eatinorders.tobe.domain.restaurant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RestaurantTableNumberOfGuestsTest {

    @DisplayName("가게 테이블 고객 수를 생성할 수 있다.")
    @Test
    void create() {
        // given
        int numberOfGuests = 2;

        // when
        RestaurantTableNumberOfGuests restaurantTableName = new RestaurantTableNumberOfGuests(
            numberOfGuests);

        // then
        assertThat(restaurantTableName.getValue()).isEqualTo(numberOfGuests);
    }

    @DisplayName("가게 테이블 고객 수를 마이너스로 생성 시, 예외가 발생한다.")
    @Test
    void createWithMinusNumber() {
        // given
        int numberOfGuests = -1;

        // when then
        assertThatThrownBy(() -> new RestaurantTableNumberOfGuests(numberOfGuests))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(RestaurantTableNumberOfGuests.ERROR_MESSAGE_MINUS);
    }
}
