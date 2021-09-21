package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Name;
import kitchenpos.common.domain.Price;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static kitchenpos.menus.tobe.domain.MenuGroupFixtures.menuGroup;

public class MenuFixtures {
    public static Menu menu(final Name name, final long price, final boolean displayed, MenuProduct... menuProducts) {
        return new Menu(UUID.randomUUID(), name, new Price(BigDecimal.valueOf(price)), menuGroup(), displayed, Arrays.asList(menuProducts));
    }
}
