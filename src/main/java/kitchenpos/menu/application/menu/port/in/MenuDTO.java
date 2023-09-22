package kitchenpos.menu.application.menu.port.in;

import static kitchenpos.support.ParameterValidateUtils.checkNotNull;

import java.util.UUID;
import kitchenpos.menu.domain.menu.MenuName;
import kitchenpos.menu.domain.menu.MenuNew;
import kitchenpos.menu.domain.menu.MenuPrice;

public final class MenuDTO {

    private final UUID id;
    private final MenuName name;
    private final MenuPrice price;

    public MenuDTO(final MenuNew menu) {
        checkNotNull(menu, "menu");

        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
    }

    public UUID getId() {
        return id;
    }

    public MenuName getName() {
        return name;
    }

    public MenuPrice getPrice() {
        return price;
    }
}
