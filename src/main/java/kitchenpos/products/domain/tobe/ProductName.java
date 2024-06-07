package kitchenpos.products.domain.tobe;

import jakarta.persistence.Embeddable;
import kitchenpos.common.domain.ProfanityValidator;

import java.util.Objects;

@Embeddable
public class ProductName {

  private String name;

  protected ProductName() {
  }

  private ProductName(String name, ProfanityValidator profanityValidator) {
    validate(name, profanityValidator);
    this.name = name;
  }

  public static ProductName from(final String name, ProfanityValidator profanityValidator) {
    return new ProductName(name, profanityValidator);
  }

  private void validate(String name, ProfanityValidator profanityValidator) {
    if (Objects.isNull(name)) {
      throw new IllegalArgumentException("상품의 이름이 올바르지 않으면 등록할 수 없다.");
    }
    if (profanityValidator.containsProfanity(name)) {
      throw new IllegalArgumentException("상품의 이름에는 비속어가 포함될 수 없다.");
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
    ProductName that = (ProductName) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
