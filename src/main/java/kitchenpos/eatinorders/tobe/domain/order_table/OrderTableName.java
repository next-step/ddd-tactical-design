package kitchenpos.eatinorders.tobe.domain.order_table;

import java.util.Objects;

public class OrderTableName {
  private String name;

  protected OrderTableName() {
  }

  public OrderTableName(String name) {
    validate(name);
    this.name = name;
  }

  private static void validate(String name) {
    if (Objects.isNull(name) || name.isEmpty()) {
      throw new IllegalArgumentException();
    }
  }

  public String getName() {
    return name;
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

  @Override
  public String toString() {
    return "OrderTableName{" +
        "name='" + name + '\'' +
        '}';
  }
}
