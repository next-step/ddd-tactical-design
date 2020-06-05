package kitchenpos.menus.bo;

import java.util.List;
import kitchenpos.menus.model.MenuGroupCreateRequest;
import kitchenpos.menus.tobe.domain.group.MenuGroup;
import kitchenpos.menus.tobe.domain.group.MenuGroupRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MenuGroupBo {

    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupBo(MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroup create(final MenuGroupCreateRequest request) {
        return menuGroupRepository.save(new MenuGroup(request.getName()));
    }

    public List<MenuGroup> list() {
        return menuGroupRepository.findAll();
    }
}
