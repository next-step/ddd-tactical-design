package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.ui.dto.MenuGroupRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroup create(final MenuGroupRequest request) {
        final MenuGroup menuGroup = request.toEntity();
        return menuGroupRepository.save(menuGroup);
    }

    @Transactional(readOnly = true)
    public MenuGroup findById(final UUID menuGroupId) {
        return menuGroupRepository.findById(menuGroupId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public List<MenuGroup> findAll() {
        return menuGroupRepository.findAll();
    }
}
