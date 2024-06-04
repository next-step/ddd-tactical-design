package kitchenpos.menus.tobe.domain;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.Optional;
import kitchenpos.products.domain.Product;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
  @Column(name = "seq")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long seq;

  @ManyToOne(optional = false)
  @JoinColumn(
      name = "product_id",
      columnDefinition = "binary(16)",
      foreignKey = @ForeignKey(name = "fk_menu_product_to_product"))
  private Product product;

  @Embedded private MenuProductQuantity quantity;

  protected MenuProduct() {}

  protected MenuProduct(final Product product, final long quantity) {
    if (Objects.isNull(product)) {
      throw new IllegalArgumentException("상품이 존재하지 않습니다.");
    }

    this.product = product;
    this.quantity = new MenuProductQuantity(quantity);
  }

  public static MenuProduct of(final Optional<Product> optProduct, final long quantity) {
    if (optProduct.isEmpty()) {
      throw new IllegalArgumentException("상품이 존재하지 않습니다.");
    }

    return new MenuProduct(optProduct.get(), quantity);
  }

  public String getMenuProductName() {
    return product.getName();
  }

  public Long getQuantity() {
    return quantity.getQuantity();
  }
}
