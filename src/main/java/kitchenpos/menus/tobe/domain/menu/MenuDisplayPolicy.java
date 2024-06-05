package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.supports.domain.tobe.Price;

public interface MenuDisplayPolicy {
  void validateSatisfied(Price price, MenuProducts menuProducts);
}
