package kitchenpos.menu.domain.service;

import java.math.BigDecimal;

public interface MenuCreatePolicy {
    BigDecimal validatePrice(BigDecimal price);
    String validateMenuName(String name, MenuPurgomalumClient purgomalumClient);
}
