package kitchenpos.menus.tobe.domain.menu_group;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {
  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;

  @Embedded
  @Column(name = "name", nullable = false)
  private MenuGroupName name;

  protected MenuGroup() {
  }

  public MenuGroup(MenuGroupName name) {
    this.id = UUID.randomUUID();
    this.name = name;
  }

  public UUID getId() {
    return id;
  }

  public MenuGroupName getName() {
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
    MenuGroup menuGroup = (MenuGroup) o;
    return Objects.equals(id, menuGroup.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "MenuGroup{" +
        "id=" + id +
        ", name=" + name +
        '}';
  }
}
