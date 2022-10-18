package kitchenpos.eatinorders.tobe.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderTableName {

  @Column(name = "name", nullable = false)
  private String name;

  public static OrderTableName from(String name) {
    return new OrderTableName(name);
  }

  protected OrderTableName() {

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
