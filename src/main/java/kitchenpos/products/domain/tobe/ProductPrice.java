package kitchenpos.products.domain.tobe;

import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;
//    - 상품의 가격이 올바르지 않으면 등록할 수 없다.
//    - 상품의 가격은 0원 이상이어야 한다.

//    - 상품의 가격을 변경할 수 있다.
//    - 상품의 가격이 올바르지 않으면 변경할 수 없다.
//    - 상품의 가격은 0원 이상이어야 한다.
//    - 상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.
@Embeddable
public class ProductPrice {

  private BigDecimal price;

  protected ProductPrice() {
  }

  private ProductPrice(BigDecimal price) {
    validate(price);
    this.price = price;
  }

  public static ProductPrice from(long price) {
    return new ProductPrice(BigDecimal.valueOf(price));
  }

  public static ProductPrice from(final BigDecimal price) {
    return new ProductPrice(price);
  }
  private void validate(BigDecimal price) {
    Objects.requireNonNull(price, "상품의 가격이 올바르지 않으면 변경할 수 없다.");

    if (price.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalStateException("상품의 가격은 0원 이상이어야 한다.");
    }
  }

  public ProductPrice add(final ProductPrice price){
    BigDecimal addedPrice = price.getPrice();

    return ProductPrice.from(this.price.add(addedPrice));
  }

  public BigDecimal getPrice(){
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
    ProductPrice that = (ProductPrice) o;
    return Objects.equals(price, that.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(price);
  }
}
