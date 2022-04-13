package kitchenpos.products.tobe.domain;

import kitchenpos.support.domain.Value;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ProductName extends Value {

  private String name;

  protected ProductName() {/*no-op*/}

  public ProductName(String name, Profanities profanities) {
    validate(name, profanities);
    this.name = name;
  }

  private void validate(String name, Profanities profanities) {
    if (Objects.isNull(name) || name.trim().isEmpty()) {
      throw new IllegalArgumentException("이름은 비어있을 수 없습니다.");
    }

    if (profanities.containsProfanity(name)) {
      throw new IllegalArgumentException("이름에 욕설이 있을 수 없습니다.");
    }
  }

}
