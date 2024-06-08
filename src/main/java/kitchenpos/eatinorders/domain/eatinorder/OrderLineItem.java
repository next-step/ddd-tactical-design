package kitchenpos.eatinorders.domain.eatinorder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.menus.domain.tobe.menu.Menu;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {

  @Column(name = "seq")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long seq;

  @ManyToOne(optional = false)
  @JoinColumn(
      name = "menu_id",
      columnDefinition = "binary(16)",
      foreignKey = @ForeignKey(name = "fk_order_line_item_to_menu")
  )
  private Menu menu;

  @Column(name = "quantity", nullable = false)
  private long quantity;

  @Transient
  private UUID menuId;

  @Transient
  private BigDecimal price;

  protected OrderLineItem() {
  }
}
