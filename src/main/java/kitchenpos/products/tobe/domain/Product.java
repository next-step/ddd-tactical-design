package kitchenpos.products.tobe.domain;

import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  private DisplayedName name;

  @Embedded
  private Price price;

  protected Product() {
  }

  public Product(DisplayedName name, Price price) {
    this.name = name;
    this.price = price;
  }

  public Long getId() {
    return id;
  }

  public DisplayedName getName() {
    return name;
  }

  public Price getPrice() {
    return price;
  }

  public Product changePrice(Price price) {
    this.price = price;
    return this;
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
}
