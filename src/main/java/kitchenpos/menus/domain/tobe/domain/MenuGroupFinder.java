package kitchenpos.menus.domain.tobe.domain;

import java.util.NoSuchElementException;
import java.util.UUID;

import kitchenpos.common.DomainService;

@DomainService
public class MenuGroupFinder {
    private final ToBeMenuGroupRepository menuGroupRepository;

    public MenuGroupFinder(ToBeMenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    public ToBeMenuGroup find(final UUID menuGroupId) {
        return menuGroupRepository.findById(menuGroupId)
            .orElseThrow(NoSuchElementException::new);
    }
}
