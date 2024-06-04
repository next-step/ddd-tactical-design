package kitchenpos.menus.application;

import kitchenpos.menus.application.dto.MenuGroupRequest;
import kitchenpos.menus.domain.tobe.menugroup.MenuGroup;
import kitchenpos.menus.domain.tobe.menugroup.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroup create(final MenuGroupRequest request) {
        final MenuGroup menuGroupRequest = MenuGroup.of(request.getId(), request.getName());

        return menuGroupRepository.save(menuGroupRequest);
    }

    @Transactional(readOnly = true)
    public List<MenuGroup> findAll() {
        return menuGroupRepository.findAll();
    }
}
