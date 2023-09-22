package kitchenpos.menu.application.menugroup.port.in;

import static kitchenpos.support.ParameterValidateUtils.checkNotNull;

import java.util.UUID;
import kitchenpos.menu.domain.menugroup.MenuGroupNew;
import kitchenpos.support.vo.Name;

public final class MenuGroupDTO {

    private final UUID id;
    private final Name name;

    public MenuGroupDTO(final MenuGroupNew entity) {
        checkNotNull(entity, "entity");

        id = entity.getId();
        name = entity.getName();
    }

    public UUID getId() {
        return id;
    }

    public Name getName() {
        return name;
    }
}
