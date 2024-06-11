package kitchenpos.menus.domain.menu;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuName {
  @Column(name = "name", nullable = false)
  private String name;

  protected MenuName() {}

  protected MenuName(String name, MenuPurgomalumClient menuPurgomalumClient) {
    if (Objects.isNull(name) || name.isEmpty()) {
      throw new IllegalArgumentException("메뉴 이름을 입력해주세요.");
    }

    if (menuPurgomalumClient.containsProfanity(name)) {
      throw new IllegalArgumentException("메뉴 이름에 비속어가 들어갈 수 없습니다.");
    }

    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MenuName menuName = (MenuName) o;
    return Objects.equals(name, menuName.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
