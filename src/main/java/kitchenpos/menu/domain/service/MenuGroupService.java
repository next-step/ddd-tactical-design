package kitchenpos.menu.domain.service;

import java.util.List;
import kitchenpos.menu.domain.model.MenuGroupVo;

public interface MenuGroupService {
    MenuGroupVo.GroupInfo create(final MenuGroupVo.Create request);
    List<MenuGroupVo.GroupInfo> findAll();
}
