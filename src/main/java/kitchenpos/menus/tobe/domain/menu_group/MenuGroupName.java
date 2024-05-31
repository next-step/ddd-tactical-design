package kitchenpos.menus.tobe.domain.menu_group;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuGroupName {
  private String name;

  protected MenuGroupName() {
  }

  public MenuGroupName(String name) {
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
    MenuGroupName that = (MenuGroupName) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "MenuGroupName{" +
        "name='" + name + '\'' +
        '}';
  }
}
