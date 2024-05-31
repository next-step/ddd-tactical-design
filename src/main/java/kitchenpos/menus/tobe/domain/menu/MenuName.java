package kitchenpos.menus.tobe.domain.menu;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuName {
  private String name;

  protected MenuName() {
  }

  public MenuName(MenuProfanityValidator menuProfanityValidator, String name) {
    validate(menuProfanityValidator, name);
    this.name = name;
  }

  private void validate(MenuProfanityValidator menuProfanityValidator, String name) {
    if (Objects.isNull(name)) {
      throw new IllegalArgumentException();
    }
    menuProfanityValidator.validate(name);
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
    MenuName menuName = (MenuName) o;
    return Objects.equals(name, menuName.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }

  @Override
  public String toString() {
    return "MenuName{" +
        "name='" + name + '\'' +
        '}';
  }
}
