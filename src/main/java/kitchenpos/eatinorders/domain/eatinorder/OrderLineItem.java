package kitchenpos.eatinorders.domain.eatinorder;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import kitchenpos.common.domain.Price;

@Table(name = "order_line_item")
@Entity
public class OrderLineItem {

  @Column(name = "seq")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long seq;

  @Column(name = "quantity", nullable = false)
  private long quantity;

  @Column(name= "menu_id", nullable = false)
  private UUID menuId;

  @Embedded
  private Price price;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(
      name = "order_id",
      columnDefinition = "binary(16)",
      foreignKey = @ForeignKey(name = "fk_order_line_items_to_orders")
  )
  private Order order;

  protected OrderLineItem() {
  }
}
