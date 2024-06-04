package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.supports.domain.tobe.Price;
import org.springframework.stereotype.Component;

@Component
public class DefaultMenuDisplayPolicy implements MenuDisplayPolicy {
  @Override
  public void validateSatisfied(Price price, MenuProducts menuProducts) {
    if(price.isGreaterThan(menuProducts.getTotalPrice())) {
      throw new IllegalStateException();
    }
  }
}
