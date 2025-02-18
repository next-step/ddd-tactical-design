package kitchenpos.menu.domain.service;

import java.util.List;
import kitchenpos.menu.domain.entity.MenuGroup;

public interface MenuGroupService {
    MenuGroup create(final MenuGroup request);
    List<MenuGroup> findAll();
}
