package kitchenpos.products.tobe.domain;

import java.util.Objects;

public class DisplayedName {

  private final String name;

  public static DisplayedName from(String name) {
    return new DisplayedName(name);
  }

  public static DisplayedName from(String name, DisplayNameValidator validator) {
    DisplayedName displayedName = new DisplayedName(name);
    validator.validate(displayedName);
    return displayedName;
  }

  private DisplayedName(String name) {
    if (Objects.isNull(name) || name.isBlank()) {
      throw new IllegalArgumentException();
    }
    this.name = name;
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
    DisplayedName that = (DisplayedName) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
