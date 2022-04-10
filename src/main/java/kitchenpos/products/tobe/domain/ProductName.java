package kitchenpos.products.tobe.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {

  private String name;

  protected ProductName() {/*no-op*/}

  public ProductName(String name) {
    validate(name);
    this.name = name;
  }

  private void validate(String name) {
    if (Objects.isNull(name) || name.trim().isEmpty()) {
      throw new IllegalArgumentException("이름은 비어있을 수 없습니다.");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProductName that = (ProductName) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
