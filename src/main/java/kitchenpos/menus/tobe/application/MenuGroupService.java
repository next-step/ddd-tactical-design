package kitchenpos.menus.tobe.application;

import java.util.List;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MenuGroupService {

    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    public String create(String name) {
        return menuGroupRepository.save(new MenuGroup(name)).getName();
    }

    public List<MenuGroup> list() {
        return menuGroupRepository.findAll();
    }
}
