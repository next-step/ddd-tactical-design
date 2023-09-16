package kitchenpos.menus.tobe.domain;

import kitchenpos.common.DomainService;

import java.util.Objects;

@DomainService
public class MenuDisplayedNamePolicy {
    private final MenuDisplayedNameProfanities productDisplayedNameProfanities;

    public MenuDisplayedNamePolicy(MenuDisplayedNameProfanities menuDisplayedNameProfanities) {
        this.productDisplayedNameProfanities = menuDisplayedNameProfanities;
    }

    public void validateDisplayName(MenuDisplayedName displayName) {
        if (Objects.isNull(displayName.getValue()) || this.containsProfanity(displayName)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean containsProfanity(MenuDisplayedName displayedName) {
        return productDisplayedNameProfanities.containsProfanity(displayedName.getValue());
    }
}
