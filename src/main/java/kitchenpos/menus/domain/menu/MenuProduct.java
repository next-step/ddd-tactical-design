package kitchenpos.menus.domain.menu;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
  @Column(name = "seq")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long seq;

  @Embedded private MenuProductQuantity quantity;

  @Column(name = "product_id")
  private UUID productId;

  @Transient private MenuProductPrice productPrice;

  protected MenuProduct() {}

  protected MenuProduct(final UUID productId, final long quantity, final BigDecimal productPrice) {
    if (Objects.isNull(productPrice) || productPrice.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("금액을 입력하지 않았거나 음수를 입력할 수 없습니다.");
    }

    this.productId = productId;
    this.productPrice = new MenuProductPrice(productPrice);
    this.quantity = new MenuProductQuantity(quantity);
  }

  public static MenuProduct of(
      final UUID productId, final long quantity, final BigDecimal productPrice) {
    return new MenuProduct(productId, quantity, productPrice);
  }

  public BigDecimal getProductPrice() {
    return this.productPrice.getProductPrice();
  }

  public void changeProductPrice(final BigDecimal price) {
    this.productPrice = new MenuProductPrice(price);
  }

  public BigDecimal multiplyByProductPriceAndQuantity() {
    final BigDecimal quantity = BigDecimal.valueOf(this.quantity.getQuantity());
    return this.productPrice.getProductPrice().multiply(quantity);
  }

  public Long getQuantity() {
    return quantity.getQuantity();
  }

  public UUID getProductId() {
    return productId;
  }
}
