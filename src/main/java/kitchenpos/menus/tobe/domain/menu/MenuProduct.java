package kitchenpos.menus.tobe.domain.menu;


import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "menu_product")
public class MenuProduct {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "menu_id")
  private Menu menu;

  @Column(nullable = false)
  private String productName;

  @Column(nullable = false)
  private BigDecimal productPrice;

  @Column(nullable = false)
  private Long productId;

  @Embedded
  private MenuProductQuantity quantity;

  protected MenuProduct() {
  }

  public MenuProduct(String productName, BigDecimal productPrice, Long productId,
      MenuProductQuantity quantity) {
    this.productName = productName;
    this.productPrice = productPrice;
    this.productId = productId;
    this.quantity = quantity;
  }

  public BigDecimal calculateAmount() {
    return productPrice.multiply(BigDecimal.valueOf(quantity.getValue()));
  }

  public void connectRelation(Menu menu) {
    this.menu = menu;
  }
}
