package kitchenpos.menus.application.loader.impl;

import kitchenpos.menugroups.domain.MenuGroupId;
import kitchenpos.menugroups.domain.MenuGroupRepository;
import kitchenpos.menus.application.loader.MenuGroupLoader;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DefaultMenuGroupLoader implements MenuGroupLoader {
    private final MenuGroupRepository menuGroupRepository;

    public DefaultMenuGroupLoader(MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Override
    public boolean exists(UUID menuGroupId) {
        return menuGroupRepository.findById(new MenuGroupId(menuGroupId))
                .isPresent();
    }
}
