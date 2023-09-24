package kitchenpos.menus.service;

import java.util.List;
import java.util.UUID;

import kitchenpos.menus.application.dto.ChangeMenuPriceRequest;
import kitchenpos.menus.application.dto.CreateMenuRequest;
import kitchenpos.menus.application.dto.MenuProductDto;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.products.domain.Product;

public class MenuRequestFixture {
    private String name;
    private Long price;
    private UUID menuGroupId;
    private boolean displayed;
    private List<MenuProductDto> menuProducts;

    public MenuRequestFixture() {
        name = "치킨";
        price = 10000L;
        menuGroupId = UUID.randomUUID();
        displayed = true;
        menuProducts = List.of();
    }

    public static MenuRequestFixture builder() {
        return new MenuRequestFixture();
    }

    public static MenuRequestFixture builder(MenuGroup menuGroup) {
        return builder()
                .menuGroupId(menuGroup.getId());
    }

    public static MenuRequestFixture builder(MenuGroup menuGroup, Product product) {
        return builder(menuGroup)
                .menuProducts(
                        List.of(MenuProductDtoFixture.builder(product).build())
                );
    }

    public MenuRequestFixture name(String name) {
        this.name = name;
        return this;
    }

    public MenuRequestFixture price(Long price) {
        this.price = price;
        return this;
    }

    public MenuRequestFixture menuGroupId(UUID menuGroupId) {
        this.menuGroupId = menuGroupId;
        return this;
    }

    public MenuRequestFixture displayed(boolean displayed) {
        this.displayed = displayed;
        return this;
    }

    public MenuRequestFixture menuProducts(List<MenuProductDto> menuProducts) {
        this.menuProducts = menuProducts;
        return this;
    }

    public CreateMenuRequest buildCreateRequest() {
        return new CreateMenuRequest(name, price, menuGroupId, displayed, menuProducts);
    }

    public ChangeMenuPriceRequest buildChangePriceRequest() {
        return new ChangeMenuPriceRequest(price);
    }

}
