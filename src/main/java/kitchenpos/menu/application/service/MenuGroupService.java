package kitchenpos.menu.application.service;

import kitchenpos.menu.application.port.out.LoadMenuGroupPort;
import kitchenpos.menu.application.port.out.SaveMenuGroupPort;
import kitchenpos.menu.application.service.model.CreateMenuGroupRequest;
import kitchenpos.menu.domain.exception.MenuGroupNameValidationException;
import kitchenpos.menu.domain.model.MenuGroup;
import kitchenpos.menu.domain.model.ProfanityFilteringMenuGroupNameValidator;
import kitchenpos.shared.port.out.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class MenuGroupService {
    private final LoadMenuGroupPort loadMenuGroupPort;
    private final SaveMenuGroupPort saveMenuGroupPort;
    private final PurgomalumClient purgomalumClient;

    public MenuGroupService(
            final LoadMenuGroupPort loadMenuGroupPort,
            final SaveMenuGroupPort saveMenuGroupPort,
            final PurgomalumClient purgomalumClient
    ) {
        this.loadMenuGroupPort = loadMenuGroupPort;
        this.saveMenuGroupPort = saveMenuGroupPort;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public MenuGroup create(final CreateMenuGroupRequest request) {
        final MenuGroup menuGroup = MenuGroup.create(UUID.randomUUID(), request.getName(), getProfanityFilteringMenuGroupNameValidator());
        return saveMenuGroupPort.save(menuGroup);
    }

    @Transactional(readOnly = true)
    public List<MenuGroup> findAll() {
        return loadMenuGroupPort.findAll();
    }

    private ProfanityFilteringMenuGroupNameValidator getProfanityFilteringMenuGroupNameValidator() {
        return name -> {
            if (purgomalumClient.containsProfanity(name)) {
                throw new MenuGroupNameValidationException("음식 그룹 이름에 비속어가 포함되어 있습니다. name=" + name);
            }
        };
    }
}
