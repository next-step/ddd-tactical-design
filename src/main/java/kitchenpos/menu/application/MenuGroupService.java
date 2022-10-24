package kitchenpos.menu.application;

import java.util.List;
import java.util.UUID;
import kitchenpos.common.name.Name;
import kitchenpos.common.name.NameFactory;
import kitchenpos.menu.tobe.application.dto.CreateMenuGroupCommand;
import kitchenpos.menu.tobe.domain.entity.MenuGroup;
import kitchenpos.menu.tobe.domain.repository.MenuGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuGroupService {

    private final MenuGroupRepository menuGroupRepository;

    private final NameFactory nameFactory;

    @Autowired
    public MenuGroupService(MenuGroupRepository menuGroupRepository, NameFactory nameFactory) {
        this.menuGroupRepository = menuGroupRepository;
        this.nameFactory = nameFactory;
    }

    @Transactional
    public MenuGroup create(final CreateMenuGroupCommand request) {
        final Name name = this.nameFactory.create(request.name);
        final MenuGroup menuGroup = new MenuGroup(UUID.randomUUID(), name);
        return menuGroupRepository.save(menuGroup);
    }

    @Transactional(readOnly = true)
    public List<MenuGroup> findAll() {
        return menuGroupRepository.findAll();
    }
}
