package kitchenpos.menus.domain.menugroup;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuGroupName {
  @Column(name = "name", nullable = false)
  private String name;

  protected MenuGroupName() {}

  protected MenuGroupName(String name) {
    if (Objects.isNull(name) || name.isBlank()) {
      throw new IllegalArgumentException("메뉴 그룹 이름을 빈값으로 입력할 수 없습니다.");
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
    MenuGroupName that = (MenuGroupName) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
