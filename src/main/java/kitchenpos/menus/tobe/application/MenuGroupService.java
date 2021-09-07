package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.dto.CreateMenuGroupRequest;
import kitchenpos.menus.tobe.dto.MenuGroupResponse;
import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("TobeMenuGroupService")
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;
    private final PurgomalumClient purgomalumClient;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository, final PurgomalumClient purgomalumClient) {
        this.menuGroupRepository = menuGroupRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public MenuGroupResponse create(final CreateMenuGroupRequest request) {
        final MenuGroup menuGroup = request.toMenuGroup();
        menuGroup.validateName(purgomalumClient::containsProfanity);
        return MenuGroupResponse.from(menuGroupRepository.save(menuGroup));
    }

    @Transactional(readOnly = true)
    public List<MenuGroupResponse> findAll() {
        return menuGroupRepository.findAll()
                .stream().map(MenuGroupResponse::from)
                .collect(Collectors.toList());
    }
}
