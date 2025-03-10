package kitchenpos.eatinorders.tobe.domain.restaurant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

class RestaurantTableNameTest {

    @DisplayName("이름으로 가게 테이블 이름을 생성할 수 있다.")
    @Test
    void create() {
        // given
        String name = "1번 테이블";

        // when
        RestaurantTableName restaurantTableName = new RestaurantTableName(name);

        // then
        assertThat(restaurantTableName.getValue()).isEqualTo(name);
    }

    @DisplayName("이름 없이 가게 테이블 이름을 생성 시, 예외가 발생한다.")
    @ParameterizedTest
    @NullSource
    @EmptySource
    void createWithNullName(String name) {
        // when then
        assertThatThrownBy(() -> new RestaurantTableName(name))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(RestaurantTableName.ERROR_MESSAGE_EMPTY);
    }
}
