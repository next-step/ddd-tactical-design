package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NumberOfGuestTest {

    @DisplayName("손님 수는 0보다 작을수 없다")
    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -100, -1000})
    void test1(int numberOfGuest) {
        //when && then
        assertThatThrownBy(
            () -> new NumberOfGuest(numberOfGuest)
        ).isInstanceOf(IllegalArgumentException.class)
            .hasMessage("손님 수는 0보다 적을수 없습니다");
    }

    @DisplayName("손님 수는 동등성을 보장받아야 한다")
    @Test
    void test2 (){
        //given
        NumberOfGuest two_1 = new NumberOfGuest(2);
        NumberOfGuest two_2 = new NumberOfGuest(2);

        //then
        assertThat(two_1).isEqualTo(two_2);
    }
}
