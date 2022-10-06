package kitchenpos.products.tobe.domain;

import kitchenpos.global.vo.Price;
import kitchenpos.products.tobe.vo.ProductName;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {

  @Column(name = "id", columnDefinition = "binary(16)")
  @Id
  private UUID id;

  @Embedded
  private ProductName name;

  @Embedded
  private Price price;

  protected Product() {

  }

  public Product(ProductName name, Price price) {
    this.name = name;
    this.price = price;
  }

  public Product(UUID id, ProductName name, Price price) {
    this(name, price);
    this.id = id;
  }

  public void changePrice(Price price) {
    this.price = price;
  }

  public UUID getId() {
    return id;
  }

  public ProductName getName() {
    return name;
  }

  public Price getPrice() {
    return price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Product product = (Product) o;

    if (id != null ? !id.equals(product.id) : product.id != null) return false;
    if (name != null ? !name.equals(product.name) : product.name != null) return false;
    return price != null ? price.equals(product.price) : product.price == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (price != null ? price.hashCode() : 0);
    return result;
  }
}
