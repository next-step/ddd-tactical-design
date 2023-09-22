package kitchenpos.menu.domain.menu;

import kitchenpos.support.vo.Name;

public final class MenuNameAccessor {

    public static MenuName create(final Name name) {
        return MenuName.create(name);
    }

    private MenuNameAccessor() {
        throw new UnsupportedOperationException();
    }
}
