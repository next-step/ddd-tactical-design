package kitchenpos.menus.tobe.application;

import java.util.List;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.entity.MenuGroup;
import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuQueryHandler {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;

    public MenuQueryHandler(MenuRepository menuRepository, MenuGroupRepository menuGroupRepository) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAllMenu() {
        return menuRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<MenuGroup> findAllMenuGroup() {
        return menuGroupRepository.findAll();
    }
}
