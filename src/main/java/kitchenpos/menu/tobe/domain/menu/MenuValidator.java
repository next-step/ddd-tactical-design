package kitchenpos.menu.tobe.domain.menu;

import java.util.UUID;

public interface MenuValidator {
    void validateMenuPrice(MenuProducts menuProducts, MenuPrice menuPrice);
    void validateMenuProductSize(MenuProducts menuProducts);
    void validateMenuGroup(UUID menuGroupId);

}
