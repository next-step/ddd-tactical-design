package kitchenpos.menus.domain.tobe.domain;

import java.util.NoSuchElementException;
import java.util.UUID;

import kitchenpos.common.DomainService;

@DomainService
public class MenuGroupValidator {
    private final ToBeMenuGroupRepository menuGroupRepository;

    public MenuGroupValidator(ToBeMenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    public ToBeMenuGroup retrieveMenuGroup(final UUID menuGroupId) {
        return menuGroupRepository.findById(menuGroupId)
            .orElseThrow(NoSuchElementException::new);
    }
}
