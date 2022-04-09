package kitchenpos.ordertable.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NumberOfGuestsTest {

  @DisplayName("방문한 손님 수는 0명 이상이어야 한다.")
  @Test
  void emptyNameTest() {
    //given
    int given = -1;

    //when & then
    assertThatThrownBy(() -> new NumberOfGuests(given))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("방문한 손님 수를 비교할 수 있다.")
  @Test
  void equalsTest() {
    //given
    int given = 4;
    NumberOfGuests name = new NumberOfGuests(given);
    NumberOfGuests theOther = new NumberOfGuests(given);

    //when & then
    assertThat(name.equals(theOther)).isTrue();
  }

}
