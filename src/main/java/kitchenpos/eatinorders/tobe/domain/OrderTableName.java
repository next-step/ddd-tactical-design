package kitchenpos.eatinorders.tobe.domain;

import java.util.Objects;

public class OrderTableName {

  private final String name;

  public static OrderTableName from(String name) {
    return new OrderTableName(name);
  }

  private OrderTableName(String name) {
    if (Objects.isNull(name) || name.isBlank()) {
      throw new IllegalArgumentException();
    }
    this.name = name;
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
