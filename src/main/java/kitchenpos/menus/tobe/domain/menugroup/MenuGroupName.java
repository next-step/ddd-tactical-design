package kitchenpos.menus.tobe.domain.menugroup;

import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class MenuGroupName {

  private static final String NAME_MUST_NOT_BE_EMPTY = "메뉴 그룹 이름은 빈 값이 아니어야 합니다. 입력 값 : %s";

  private String name;

  protected MenuGroupName() {}

  public MenuGroupName(String name) {
    validateName(name);
    this.name = name;
  }

  private void validateName(String name) {
    if (isEmptyName(name)) {
      throw new IllegalArgumentException(String.format(NAME_MUST_NOT_BE_EMPTY, name));
    }
  }

  private boolean isEmptyName(String name) {
    return name == null || name.isEmpty();
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
}
