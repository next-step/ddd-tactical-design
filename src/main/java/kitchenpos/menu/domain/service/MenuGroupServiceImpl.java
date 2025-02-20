package kitchenpos.menu.domain.service;

import java.util.List;
import java.util.UUID;
import kitchenpos.menu.domain.entity.MenuGroup;
import kitchenpos.menu.domain.model.MenuGroupVo;
import kitchenpos.menu.domain.repository.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MenuGroupServiceImpl implements MenuGroupService {

    private final MenuGroupRepository menuGroupRepository;
    private final MenuGroupCreatePolicy menuGroupCreatePolicy;
    private final MenuPurgomalumClient menuPurgomalumClient;

    public MenuGroupServiceImpl(
        final MenuGroupRepository menuGroupRepository,
        final MenuGroupCreatePolicy menuGroupCreatePolicy,
        final MenuPurgomalumClient menuPurgomalumClient
    ) {
        this.menuGroupRepository = menuGroupRepository;
        this.menuGroupCreatePolicy = menuGroupCreatePolicy;
        this.menuPurgomalumClient = menuPurgomalumClient;
    }

    @Override
    public MenuGroupVo.GroupInfo create(final MenuGroupVo.Create request) {
        final String name = menuGroupCreatePolicy.validateGroupName(request.name(), menuPurgomalumClient);

        return MenuGroupVo.GroupInfo.fromEntity(
            menuGroupRepository.save(new MenuGroup(UUID.randomUUID(), name))
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
