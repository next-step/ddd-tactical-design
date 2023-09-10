package kitchenpos.menus.application.fixtures;

import kitchenpos.common.domain.ProfanityPolicy;
import kitchenpos.menus.dto.MenuCreateRequest;
import kitchenpos.menus.dto.MenuProductRequest;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.stream.Collectors;

import static kitchenpos.menus.application.fixtures.MenuGroupFixture.menuGroup;
import static kitchenpos.products.fixture.ProductFixture.product;

public class MenuFixture {

    private static final ProfanityPolicy DEFAULT_PROFANITY_POLICY = text -> false;

    public static Menu menu() {
        return menu(19_000L, true, menuProduct());
    }

    public static Menu menu(final long price, final MenuProduct... menuProducts) {
        return menu(price, true, menuProducts);
    }

    public static MenuCreateRequest menuCreate(final long price, final boolean displayed, MenuGroup menuGroup, final MenuProduct... menuProducts) {
        return new MenuCreateRequest(
                BigDecimal.valueOf(price),
                menuGroup.getId(),
                Arrays.stream(menuProducts)
                        .map(MenuFixture::menuProductRequest)
                        .collect(Collectors.toUnmodifiableList()),
                "후라이드+후라이드",
                displayed
        );
    }

    public static Menu menu(final long price, final boolean displayed, final MenuProduct... menuProducts) {
        MenuGroup menuGroup = menuGroup();
        return new Menu(
                "후라이드+후라이드",
                DEFAULT_PROFANITY_POLICY,
                BigDecimal.valueOf(price),
                menuGroup,
                displayed,
                Arrays.asList(menuProducts)
        );
    }

    public static Menu menu(final long price, final boolean displayed, MenuGroup menuGroup, final MenuProduct... menuProducts) {
        return new Menu(
                "후라이드+후라이드",
                DEFAULT_PROFANITY_POLICY,
                BigDecimal.valueOf(price),
                menuGroup,
                displayed,
                Arrays.asList(menuProducts)
        );
    }


    public static MenuProduct menuProduct() {
        return new MenuProduct(product(), 2L);
    }

    public static MenuProduct menuProduct(final Product product, final long quantity) {
        return new MenuProduct(product, quantity);
    }

    public static MenuProductRequest menuProductRequest(MenuProduct menuProduct) {
        return new MenuProductRequest(
                menuProduct.getProductId(),
                menuProduct.getQuantityValue());
    }

}
