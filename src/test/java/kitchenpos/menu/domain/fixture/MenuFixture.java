package kitchenpos.menu.domain.fixture;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menu.application.dto.MenuRequest;
import kitchenpos.menu.application.dto.MenuRequest.MenuProductCreate;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.entity.MenuProduct;
import kitchenpos.menu.domain.model.MenuGroupId;
import kitchenpos.menu.domain.model.MenuId;
import kitchenpos.menu.domain.model.MenuName;
import kitchenpos.menu.domain.model.MenuPrice;
import kitchenpos.menu.domain.model.MenuProducts;
import kitchenpos.product.domain.fixture.ProductFixture;

public record MenuFixture(UUID id, String 메뉴명, BigDecimal 메뉴가격,
                          UUID 메뉴그룹아이디, boolean 노출여부, List<MenuProductCreate> 메뉴구성품) {

    private static final String DEFAULT_MENU_NAME = "황금 올리브 치킨";
    private static final BigDecimal DEFAULT_MENU_PRICE = BigDecimal.valueOf(20_000);

    public static MenuFixture init() {
        return new MenuFixture(
            UUID.randomUUID(),
            DEFAULT_MENU_NAME,
            DEFAULT_MENU_PRICE,
            MenuGroupFixture.init().toEntity().getMenuGroupId().get(),
            true,
            java.util.List.of(MenuProductFixture.init(null).create()));
    }

    public static MenuFixture test(String 메뉴명, BigDecimal 메뉴가격,
        UUID 메뉴그룹아이디, boolean 노출여부, List<MenuProductCreate> 메뉴구성품) {
        return new MenuFixture(
            UUID.randomUUID(),
            Objects.requireNonNullElse(메뉴명, DEFAULT_MENU_NAME),
            Objects.requireNonNullElse(메뉴가격, DEFAULT_MENU_PRICE),
            Objects.requireNonNullElse(메뉴그룹아이디, MenuGroupFixture.init().toEntity().getMenuGroupId().get()),
            노출여부,
            Objects.requireNonNullElse(메뉴구성품, List.of(MenuProductFixture.init(null).create()))
        );
    }

    public Menu toEntity() {
        return new Menu(
            MenuId.of(id),
            new MenuName(메뉴명),
            MenuPrice.of(메뉴가격),
            MenuGroupId.of(메뉴그룹아이디),
            노출여부,
            new MenuProducts(메뉴구성품.stream()
                .map(메뉴구성 -> MenuProduct.fromDto(메뉴구성, MenuId.of(id)))
                .collect(Collectors.toList())));
    }

    public MenuRequest.Create create() {
        return new MenuRequest.Create(메뉴명, 메뉴가격, 메뉴그룹아이디, 노출여부, 메뉴구성품);
    }

    public MenuRequest.UpdatePrice update() {
        return new MenuRequest.UpdatePrice(id, 메뉴가격);
    }
}

