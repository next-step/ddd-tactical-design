package kitchenpos.menugroups.application;

import kitchenpos.menugroups.domain.MenuGroup;
import kitchenpos.menugroups.domain.MenuGroupId;
import kitchenpos.menugroups.domain.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroup create(final MenuGroup request) {
        return menuGroupRepository.save(request);
    }

    @Transactional(readOnly = true)
    public List<MenuGroup> findAll() {
        return menuGroupRepository.findAll();
    }

    @Transactional(readOnly = true)
    public MenuGroup findById(MenuGroupId menuGroupId) {
        return menuGroupRepository.findById(menuGroupId)
                .orElseThrow(NoSuchElementException::new);
    }

}
