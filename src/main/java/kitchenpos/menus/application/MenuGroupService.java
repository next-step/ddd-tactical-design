package kitchenpos.menus.application;


import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.NewMenuGroup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public NewMenuGroup create(final MenuGroupCreateRequest request) {
        NewMenuGroup newMenuGroup = NewMenuGroup.create(UUID.randomUUID(), request.getName());
        return menuGroupRepository.save(newMenuGroup);
    }

    @Transactional(readOnly = true)
    public List<NewMenuGroup> findAll() {
        return menuGroupRepository.findAll();
    }
}
