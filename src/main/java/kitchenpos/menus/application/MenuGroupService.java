package kitchenpos.menus.application;

import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.domain.exception.NotFoundMenuGroupException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroup create(final MenuGroup request) {
        return menuGroupRepository.save(new MenuGroup(UUID.randomUUID(), request.getName()));
    }

    @Transactional(readOnly = true)
    public List<MenuGroup> findAll() {
        return menuGroupRepository.findAll();
    }

    @Transactional(readOnly = true)
    public MenuGroup findById(final UUID menuGroupId) {
        Optional<MenuGroup> byId = menuGroupRepository.findById(menuGroupId);
        return menuGroupRepository.findById(menuGroupId)
                .orElseThrow(NotFoundMenuGroupException::new);
    }

    @Transactional(readOnly = true)
    public void validMenuGroupId(final UUID menuGroupId) {
        findById(menuGroupId);
    }
}
