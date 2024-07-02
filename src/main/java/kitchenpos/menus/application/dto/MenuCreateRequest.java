package kitchenpos.menus.application.dto;

import java.util.UUID;
import kitchenpos.menus.domain.tobe.DisplayedMenu;
import kitchenpos.menus.domain.tobe.MenuName;
import kitchenpos.menus.domain.tobe.MenuPrice;

public record MenuCreateRequest(MenuName name,
                                MenuPrice price,
                                UUID menuGroupId,
                                DisplayedMenu displayed,
                                MenuProductsCreateRequest menuProductsCreateRequest) {

}
