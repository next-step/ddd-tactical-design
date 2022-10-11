package kitchenpos.products.tobe.domain;

import io.micrometer.core.instrument.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DisplayedName {

  @Column(name = "name", nullable = false)
  private String name;

  public DisplayedName() {
  }

  public DisplayedName(String name, Boolean isProfanity) {
    if (StringUtils.isBlank(name) || (isProfanity != null && isProfanity)) {
      throw new IllegalArgumentException(String.format("잘못된 상품명입니다. 공백 또는 비속어 포함. %s", name));
    }
    this.name = name;
  }

  public String getName() {
    return name;
  }
}