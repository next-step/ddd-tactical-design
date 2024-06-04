package kitchenpos.menus.domain.tobe.menugroup;

import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {

  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;
  @Embedded
  @Column(name = "name", nullable = false)
  private MenuGroupName menuGroupName;

  protected MenuGroup() {
  }

  private MenuGroup(UUID id, MenuGroupName menuGroupName) {
    this.id = id;
    this.menuGroupName = menuGroupName;
  }

  public static MenuGroup of(UUID id, String name){
    return new MenuGroup(id, MenuGroupName.of(name));
  }

  public UUID getId() {
    return id;
  }

  public String  getMenuGroupName() {
    return menuGroupName.getName();
  }
}
