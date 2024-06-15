package kitchenpos.eatinorders.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Name {
  @Column(name = "name", nullable = false)
  private String name;

  protected Name() {}

  protected Name(final String name) {
    if (Objects.isNull(name) || name.isBlank()) {
      throw new IllegalArgumentException();
    }

    this.name = name;
  }

  public String getName() {
    return name;
  }
}
