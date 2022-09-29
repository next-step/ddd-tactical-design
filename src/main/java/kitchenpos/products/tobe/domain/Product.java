package kitchenpos.products.tobe.domain;

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
  private Price price;

  public Product() {

  }

  public Product(UUID id, DisplayedName name, Price price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public UUID getId() {
    return id;
  }

  public DisplayedName getName() {
    return name;
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
    Product product = (Product) o;
    return Objects.equals(id, product.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public void changePrice(Price price) {
    this.price = price;
  }
}
