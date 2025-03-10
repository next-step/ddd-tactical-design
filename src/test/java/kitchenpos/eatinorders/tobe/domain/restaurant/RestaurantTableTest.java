package kitchenpos.eatinorders.tobe.domain.restaurant;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RestaurantTableTest {

    @DisplayName("가게 테이블을 생성할 수 있다.")
    @Test
    void create() {
        // given
        final UUID id = UUID.randomUUID();
        final String name = "1번 테이블";
        final int numberOfGuests = 2;
        final boolean occupied = true;

        // when
        RestaurantTable restaurantTable = new RestaurantTable(id, name, numberOfGuests, occupied);

        // then
        assertThat(restaurantTable.getId()).isEqualTo(id);
        assertThat(restaurantTable.getName()).isEqualTo(name);
        assertThat(restaurantTable.getNumberOfGuests()).isEqualTo(numberOfGuests);
        assertThat(restaurantTable.isOccupied()).isEqualTo(occupied);
    }
}
