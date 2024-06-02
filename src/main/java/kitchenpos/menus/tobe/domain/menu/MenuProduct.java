package kitchenpos.menus.tobe.domain.menu;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import kitchenpos.supports.domain.tobe.Quantity;
import kitchenpos.supports.domain.tobe.Price;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
  @Column(name = "seq")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long seq;

  @Embedded
  @Column(name = "quantity", nullable = false)
  private Quantity quantity;

  @ManyToOne(optional = false)
  @JoinColumn(
      name = "product_id",
      columnDefinition = "binary(16)",
      foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
  )
  private RegisteredProduct product;

  protected MenuProduct() {
  }

  public MenuProduct(Long seq, Quantity quantity, RegisteredProduct product) {
    this.seq = seq;
    this.quantity = quantity;
    this.product = product;
  }

  public Long getSeq() {
    return seq;
  }

  public Quantity getQuantity() {
    return quantity;
  }

  public Price getPrice() {
    return this.product.getPrice().multiply(this.quantity);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MenuProduct that = (MenuProduct) o;
    return Objects.equals(seq, that.seq);
  }

  @Override
  public int hashCode() {
    return Objects.hash(seq);
  }

  @Override
  public String toString() {
    return "MenuProduct{" +
        "seq=" + seq +
        ", quantity=" + quantity +
        ", product=" + product +
        '}';
  }
}
