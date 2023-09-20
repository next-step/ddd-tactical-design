package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.common.DomainService;

import java.util.Objects;

@DomainService
public class MenuNamePolicy {
    private final MenuNameProfanities menuNameProfanities;

    public MenuNamePolicy(MenuNameProfanities menuNameProfanities) {
        this.menuNameProfanities = menuNameProfanities;
    }

    public void validateName(MenuName displayName) {
        if (Objects.isNull(displayName.getValue()) || this.containsProfanity(displayName)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean containsProfanity(MenuName name) {
        return menuNameProfanities.containsProfanity(name.getValue());
    }
}
