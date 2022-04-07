package kitchenpos.menus.tobe.domain.product;

import java.math.BigDecimal;
import java.util.Objects;


public class Product {

  private Long id;

  private DisplayedName name;

  private Price price;

  protected Product() {
  }

  public Product(DisplayedName name, BigDecimal price) {
    this.name = name;
    this.price = new Price(price);
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
