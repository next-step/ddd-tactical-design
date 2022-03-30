package kitchenpos.menus.tobe.domain.model;

import kitchenpos.global.infrastructure.external.BannedWordCheckClient;
import kitchenpos.menus.tobe.domain.vo.MenuGroupName;
import kitchenpos.products.tobe.exception.IllegalProductNameException;

import java.util.Objects;
import java.util.UUID;

public final class MenuGroup {

    private UUID id;
    private MenuGroupName name;

    public MenuGroup(final String name) {
        this.id = UUID.randomUUID();
        this.name = new MenuGroupName(name);
    }

    public UUID getId() {
        return id;
    }

    public MenuGroupName getName() {
        return name;
    }
}
