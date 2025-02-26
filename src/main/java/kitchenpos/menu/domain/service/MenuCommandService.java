package kitchenpos.menu.domain.service;

import kitchenpos.menu.domain.model.MenuId;
import kitchenpos.menu.domain.model.MenuVo;

public interface MenuCommandService {
    MenuVo.MenuInfo create(final MenuVo.Create request);
    MenuVo.MenuInfo changePrice(final MenuVo.Update request);
    MenuVo.MenuInfo display(final MenuId menuId);
    MenuVo.MenuInfo hide(final MenuId menuId);
}
