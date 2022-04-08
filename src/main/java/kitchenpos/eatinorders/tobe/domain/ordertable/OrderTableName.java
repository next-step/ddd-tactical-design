package kitchenpos.eatinorders.tobe.domain.ordertable;

import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class OrderTableName {

  private static final String NAME_MUST_NOT_BE_EMPTY = "주문테이블 이름은 빈 값이 아니어야 합니다. 입력 값 : %s";

  private String name;

  protected OrderTableName() {}

  public OrderTableName(String name) {
    validateName(name);
    this.name = name;
  }

  private void validateName(String name) {
    if (isEmptyName(name)) {
      throw new IllegalArgumentException(String.format(NAME_MUST_NOT_BE_EMPTY, name));
    }
  }

  private boolean isEmptyName(String name) {
    return name == null || name.isEmpty();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrderTableName that = (OrderTableName) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
