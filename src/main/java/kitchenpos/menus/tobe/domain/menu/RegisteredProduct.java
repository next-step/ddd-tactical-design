package kitchenpos.menus.tobe.domain.menu;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.supports.domain.tobe.Price;

@Table(name = "product")
@Entity
public class RegisteredProduct {
  @Id
  @Column(name = "id", columnDefinition = "binary(16)", nullable = false, insertable = false, updatable = false)
  private UUID id;

  @Embedded
  @Column(name = "price", nullable = false, insertable = false, updatable = false)
  private Price price;

  protected RegisteredProduct() {
  }

  public RegisteredProduct(UUID id, Price price) {
    this.id = id;
    this.price = price;
  }

  public UUID getId() {
    return id;
  }

  public Price getPrice() {
    return price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RegisteredProduct that = (RegisteredProduct) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "RegisteredProduct{" +
        "id=" + id +
        ", price=" + price +
        '}';
  }
}
