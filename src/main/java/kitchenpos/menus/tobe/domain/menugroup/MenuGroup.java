package kitchenpos.menus.tobe.domain.menugroup;

import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "menu_group")
public class MenuGroup {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  private MenuGroupName name;

  protected MenuGroup() {
  }

  public MenuGroup(String name) {
    this.name = new MenuGroupName(name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MenuGroup menuGroup = (MenuGroup) o;
    return Objects.equals(id, menuGroup.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
