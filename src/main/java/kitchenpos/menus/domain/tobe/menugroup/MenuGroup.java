package kitchenpos.menus.domain.tobe.menugroup;

import jakarta.persistence.*;

import java.util.UUID;
//    - 메뉴 그룹을 등록할 수 있다.
//    - 메뉴 그룹의 이름이 올바르지 않으면 등록할 수 없다.
//    - 메뉴 그룹의 이름은 비워 둘 수 없다.
//    - 메뉴 그룹의 목록을 조회할 수 있다.
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
}
