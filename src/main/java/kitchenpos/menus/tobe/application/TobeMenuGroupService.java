package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.MenuGroupName;
import kitchenpos.menus.tobe.domain.TobeMenuGroup;
import kitchenpos.menus.tobe.domain.TobeMenuGroupRepository;
import kitchenpos.menus.tobe.ui.MenuGroupForm;
import kitchenpos.tobeinfra.TobePurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TobeMenuGroupService {
    private final TobeMenuGroupRepository menuGroupRepository;
    private final TobePurgomalumClient purgomalumClient;

    public TobeMenuGroupService(
        final TobeMenuGroupRepository menuGroupRepository,
        final TobePurgomalumClient purgomalumClient
    ) {
        this.menuGroupRepository = menuGroupRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public MenuGroupForm create(final MenuGroupForm request) {
        MenuGroupName menuGroupName = new MenuGroupName(request.getName(), purgomalumClient);
        TobeMenuGroup saveMenuGroup = menuGroupRepository.save(new TobeMenuGroup(menuGroupName));
        return MenuGroupForm.of(saveMenuGroup);
    }

    @Transactional(readOnly = true)
    public List<MenuGroupForm> findAll() {
        return menuGroupRepository.findAll().stream()
                .map(MenuGroupForm::of)
                .collect(Collectors.toList());
    }
}
