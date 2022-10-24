package kitchenpos.menu.application;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.common.name.Name;
import kitchenpos.menu.tobe.domain.entity.MenuGroup;
import kitchenpos.menu.tobe.domain.repository.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroup create(final MenuGroup request) {
        final Name name = request.name;
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException();
        }
        final MenuGroup menuGroup = new MenuGroup(UUID.randomUUID(), name);
        return menuGroupRepository.save(menuGroup);
    }

    @Transactional(readOnly = true)
    public List<MenuGroup> findAll() {
        return menuGroupRepository.findAll();
    }
}
