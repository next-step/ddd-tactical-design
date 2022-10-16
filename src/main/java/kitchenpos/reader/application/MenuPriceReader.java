package kitchenpos.reader.application;

import java.util.UUID;
import kitchenpos.reader.domain.MenuPriceAndDisplayed;

public interface MenuPriceReader {

    MenuPriceAndDisplayed getMenuPriceAndDisplayedById(UUID menuId);
}
