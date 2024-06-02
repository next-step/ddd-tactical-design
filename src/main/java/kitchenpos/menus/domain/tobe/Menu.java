package kitchenpos.menus.domain.tobe;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuProduct;
//- 1 개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.
//    - 상품이 없으면 등록할 수 없다.
//    - 메뉴에 속한 상품의 수량은 0 이상이어야 한다.
//    - 메뉴의 가격이 올바르지 않으면 등록할 수 없다.
//    - 메뉴의 가격은 0원 이상이어야 한다.
//    - 메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.
//    - 메뉴는 특정 메뉴 그룹에 속해야 한다.
//    - 메뉴의 이름이 올바르지 않으면 등록할 수 없다.
//    - 메뉴의 이름에는 비속어가 포함될 수 없다.
//    - 메뉴의 가격을 변경할 수 있다.
//    - 메뉴의 가격이 올바르지 않으면 변경할 수 없다.
//    - 메뉴의 가격은 0원 이상이어야 한다.
//    - 메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.
//    - 메뉴를 노출할 수 있다.
//    - 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.
//    - 메뉴를 숨길 수 있다.
//    - 메뉴의 목록을 조회할 수 있다.
@Table(name = "menu")
@Entity
public class Menu {

  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;

  @Column(name = "name", nullable = false)
  private MenuName menuName;

  @Column(name = "price", nullable = false)
  private MenuPrice menuPrice;

  @ManyToOne(optional = false)
  @JoinColumn(
      name = "menu_group_id",
      columnDefinition = "binary(16)",
      foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
  )
  private MenuGroup menuGroup;

  @Column(name = "displayed", nullable = false)
  private boolean displayed;

  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(
      name = "menu_id",
      nullable = false,
      columnDefinition = "binary(16)",
      foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
  )
  @Embedded
  private MenuProducts menuProducts;

  @Transient
  private UUID menuGroupId;

  protected Menu() {
  }
}
