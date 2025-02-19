package kitchenpos.menu.application.service;

import kitchenpos.menu.application.port.out.LoadMenuGroupPort;
import kitchenpos.menu.application.port.out.SaveMenuGroupPort;
import kitchenpos.menu.application.service.model.CreateMenuGroupRequest;
import kitchenpos.menu.domain.model.MenuGroup;
import kitchenpos.shared.domain.Profanities;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class MenuGroupService {
    private final LoadMenuGroupPort loadMenuGroupPort;
    private final SaveMenuGroupPort saveMenuGroupPort;
    private final Profanities profanities;

    public MenuGroupService(
            final LoadMenuGroupPort loadMenuGroupPort,
            final SaveMenuGroupPort saveMenuGroupPort,
            final Profanities profanities
    ) {
        this.loadMenuGroupPort = loadMenuGroupPort;
        this.saveMenuGroupPort = saveMenuGroupPort;
        this.profanities = profanities;
    }

    @Transactional
    public MenuGroup create(final CreateMenuGroupRequest request) {
        final MenuGroup menuGroup = MenuGroup.create(UUID.randomUUID(), request.getName(), profanities);
        return saveMenuGroupPort.save(menuGroup);
    }

    @Transactional(readOnly = true)
    public List<MenuGroup> findAll() {
        return loadMenuGroupPort.findAll();
    }
}
