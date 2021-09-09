package kitchenpos.menus.tobe.application;

import kitchenpos.common.infra.Profanities;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupName;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.dto.CreateMenuGroupRequest;
import kitchenpos.menus.tobe.dto.MenuGroupResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("TobeMenuGroupService")
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;
    private final Profanities profanities;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository, final Profanities profanities) {
        this.menuGroupRepository = menuGroupRepository;
        this.profanities = profanities;
    }

    @Transactional
    public MenuGroupResponse create(final CreateMenuGroupRequest request) {
        final MenuGroupName menuGroupName = new MenuGroupName(request.getName(), profanities);
        return MenuGroupResponse.from(menuGroupRepository.save(new MenuGroup(menuGroupName)));
    }

    @Transactional(readOnly = true)
    public List<MenuGroupResponse> findAll() {
        return menuGroupRepository.findAll()
                .stream().map(MenuGroupResponse::from)
                .collect(Collectors.toList());
    }
}
