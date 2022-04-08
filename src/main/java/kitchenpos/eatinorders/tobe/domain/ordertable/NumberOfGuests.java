package kitchenpos.eatinorders.tobe.domain.ordertable;

import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class NumberOfGuests {

  private static final int MINIMUM_COUNT = 0;

  private static final String COUNT_MUST_NOT_BE_LESS_THAN_MINIMUM = "방문한 손님 수는 %d 이상이어야 합니다. 입력 값 : %d";

  private int count;

  protected NumberOfGuests() {
  }

  public NumberOfGuests(int count) {
    validateCount(count);
    this.count = count;
  }

  private void validateCount(int count) {
    if (isLessThanMinimum(count)) {
      throw new IllegalArgumentException(String.format(COUNT_MUST_NOT_BE_LESS_THAN_MINIMUM, MINIMUM_COUNT, count));
    }
  }

  private boolean isLessThanMinimum(int count) {
    return count < MINIMUM_COUNT;
  }

  public int getCount() {
    return count;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NumberOfGuests that = (NumberOfGuests) o;
    return count == that.count;
  }

  @Override
  public int hashCode() {
    return Objects.hash(count);
  }
}
