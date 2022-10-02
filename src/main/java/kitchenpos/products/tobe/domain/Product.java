package kitchenpos.products.tobe.domain;

import kitchenpos.common.model.DisplayedName;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "product")
@Entity
public class Product {

  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;

  @Embedded
  private DisplayedName name;

  @Embedded
  private ProductPrice price;

  protected Product() {

  }

  public Product(DisplayedName name, ProductPrice price) {
    this.name = name;
    this.price = price;
  }

  public Product(UUID id, DisplayedName name, ProductPrice price) {
    this(name, price);
    this.id = id;
  }

  public void changePrice(ProductPrice price) {
    this.price = price;
  }

  public UUID getId() {
    return id;
  }

  public DisplayedName getName() {
    return name;
  }

  public ProductPrice getPrice() {
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

    Product product = (Product) o;

    if (!Objects.equals(id, product.id)) {
      return false;
    }
    if (!Objects.equals(name, product.name)) {
      return false;
    }
    return Objects.equals(price, product.price);
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (price != null ? price.hashCode() : 0);
    return result;
  }
}
