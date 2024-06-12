package kitchenpos.takeoutorders.domain;

import jakarta.persistence.*;
import java.util.UUID;
import kitchenpos.menus.domain.menu.Menu;

@Table(name = "order_line_item")
@Entity
public class TakeoutOrderLineItem {
  @Column(name = "seq")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long seq;

  @ManyToOne(optional = false)
  @JoinColumn(
      name = "menu_id",
      columnDefinition = "binary(16)",
      foreignKey = @ForeignKey(name = "fk_order_line_item_to_menu"))
  private Menu menu;

  @Embedded private Quantity quantity;

  @Transient private UUID menuId;

  @Embedded private Price price;

  public TakeoutOrderLineItem() {}
}
