package kitchenpos.menu.application.service;

import kitchenpos.menu.application.port.out.LoadMenuGroupPort;
import kitchenpos.menu.application.port.out.SaveMenuGroupPort;
import kitchenpos.menu.domain.model.MenuGroup;
import kitchenpos.menu.domain.model.MenuGroupName;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class MenuGroupService {
    private final LoadMenuGroupPort loadMenuGroupPort;
    private final SaveMenuGroupPort saveMenuGroupPort;

    public MenuGroupService(
            final LoadMenuGroupPort loadMenuGroupPort,
            final SaveMenuGroupPort saveMenuGroupPort
    ) {
        this.loadMenuGroupPort = loadMenuGroupPort;
        this.saveMenuGroupPort = saveMenuGroupPort;
    }

    @Transactional
    public MenuGroup create(final MenuGroup request) {
        final MenuGroup menuGroup = new MenuGroup();
        menuGroup.setId(UUID.randomUUID());
        menuGroup.setName(MenuGroupName.of(request.getName()));
        return saveMenuGroupPort.save(menuGroup);
    }

    @Transactional(readOnly = true)
    public List<MenuGroup> findAll() {
        return loadMenuGroupPort.findAll();
    }
}
