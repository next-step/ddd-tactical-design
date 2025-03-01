package kitchenpos.menu.domain.service;

import java.util.List;
import java.util.UUID;
import kitchenpos.menu.domain.entity.MenuGroup;
import kitchenpos.menu.domain.model.MenuGroupId;
import kitchenpos.menu.domain.model.MenuGroupName;
import kitchenpos.menu.domain.model.MenuGroupVo;
import kitchenpos.menu.domain.repository.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DefaultMenuGroupService implements MenuGroupQueryService, MenuGroupCommandService {

    private final MenuGroupRepository menuGroupRepository;
    private final MenuPurgomalumClient menuPurgomalumClient;

    public DefaultMenuGroupService(
        final MenuGroupRepository menuGroupRepository,
        final MenuPurgomalumClient menuPurgomalumClient
    ) {
        this.menuGroupRepository = menuGroupRepository;
        this.menuPurgomalumClient = menuPurgomalumClient;
    }

    @Override
    public MenuGroupVo.GroupInfo create(final MenuGroupVo.Create request) {
        final MenuGroupName name = MenuGroupName.of(request.name(), menuPurgomalumClient);

        return MenuGroupVo.GroupInfo.fromEntity(
            menuGroupRepository.save(new MenuGroup(MenuGroupId.of(UUID.randomUUID()), name))
        );
    }

    @Transactional(readOnly = true)
    @Override
    public List<MenuGroupVo.GroupInfo> findAll() {
        return menuGroupRepository.findAll()
            .stream()
            .map(MenuGroupVo.GroupInfo::fromEntity)
            .toList();
    }
}
