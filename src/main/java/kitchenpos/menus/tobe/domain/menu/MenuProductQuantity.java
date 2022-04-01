package kitchenpos.menus.tobe.domain.menu;

import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class MenuProductQuantity {

  private static final String PRICE_MUST_BE_POSITIVE_NUMBER = "메뉴 상품 수량은 0 개 이상이어야 합니다. 입력 값 : %d";

  private long quantity;

  protected MenuProductQuantity() {}

  public MenuProductQuantity(long quantity) {
    validatePrice(quantity);
    this.quantity = quantity;
  }

  private void validatePrice(long quantity) {
    if (isNegative(quantity)) {
      throw new IllegalArgumentException(String.format(PRICE_MUST_BE_POSITIVE_NUMBER, quantity));
    }
  }

  private boolean isNegative(long quantity) {
    return quantity < 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MenuProductQuantity that = (MenuProductQuantity) o;
    return quantity == that.quantity;
  }

  @Override
  public int hashCode() {
    return Objects.hash(quantity);
  }
}
