package kitchenpos.menu.domain.service;

import java.util.List;
import java.util.UUID;
import kitchenpos.menu.domain.model.MenuVo;

public interface MenuService {
    MenuVo.MenuInfo create(final MenuVo.Create request);
    MenuVo.MenuInfo changePrice(final MenuVo.Update request);
    MenuVo.MenuInfo display(final UUID menuId);
    MenuVo.MenuInfo hide(final UUID menuId);
    List<MenuVo.MenuInfo> findAll();
}
