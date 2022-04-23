package kitchenpos.eatinorders.tobe.domain;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_line_item")
public class EatInOrderLineItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long menuId;

  private String menuName;

  private long quantity;

  private BigDecimal price;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private EatInOrder order;

  protected EatInOrderLineItem() {
  }

  public EatInOrderLineItem(Long menuId, String menuName, long quantity, BigDecimal price) {
    this.menuId = menuId;
    this.menuName = menuName;
    this.quantity = quantity;
    this.price = price;
  }
}
