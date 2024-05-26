package kitchenpos.products.tobe.domain;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName {
  private String name;

  public ProductName(ProductProfanityValidator profanityValidator, String name) {
    validate(profanityValidator, name);
    this.name = name;
  }

  protected ProductName() {
  }

  private static void validate(ProductProfanityValidator profanityValidator, String name) {
    if (Objects.isNull(name)) {
      throw new IllegalArgumentException();
    }
    profanityValidator.validate(name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductName that = (ProductName) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "ProductName{" +
        "name='" + name + '\'' +
        '}';
  }
}
