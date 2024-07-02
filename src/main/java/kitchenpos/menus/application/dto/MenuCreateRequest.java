package kitchenpos.menus.application.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import kitchenpos.menus.domain.tobe.DisplayedMenu;
import kitchenpos.menus.domain.tobe.MenuName;
import kitchenpos.menus.domain.tobe.MenuPrice;

public record MenuCreateRequest(@NotNull MenuName name,
                                @NotNull MenuPrice price,
                                @NotNull UUID menuGroupId,
                                @NotNull DisplayedMenu displayed,
                                @NotNull MenuProductsCreateRequest menuProductsCreateRequest) {

}
