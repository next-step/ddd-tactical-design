package kitchenpos.menus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.products.ProductFixture;
import kitchenpos.products.domain.Product;

public class MenuFixture {

    public static MenuCreateRequestBuilder createRequestBuilder() {
        return new MenuCreateRequestBuilder();
    }

    public static MenuUpdateRequestBuilder updateRequestBuilder() {
        return new MenuUpdateRequestBuilder();
    }

    public static Menu menu() {
        Menu menu = new Menu();
        menu.setId(UUID.randomUUID());
        menu.setMenuProducts(menuProducts());
        menu.setDisplayed(true);
        menu.setMenuGroupId(MenuGroupFixture.menuGroup().getId());
        menu.setPrice(BigDecimal.valueOf(33_000L));

        return menu;
    }

    public static List<MenuProduct> menuProducts() {
        MenuProduct friedChickenMenu = new MenuProduct();
        friedChickenMenu.setProduct(ProductFixture.product("후라이드치킨", 16_000L));
        friedChickenMenu.setQuantity(1L);

        MenuProduct garlicChickenMenu = new MenuProduct();
        garlicChickenMenu.setProduct(ProductFixture.product("갈릭치킨", 17_000L));
        garlicChickenMenu.setQuantity(1L);

        return List.of(friedChickenMenu, garlicChickenMenu);
    }

    public static class MenuCreateRequestBuilder {

        private BigDecimal price = BigDecimal.valueOf(0);
        private String name;
        private final List<MenuProduct> menuProducts = new ArrayList<>();
        private UUID menuGroupId;
        private boolean isDisplay;

        MenuCreateRequestBuilder() {
        }

        public MenuCreateRequestBuilder name(final String name) {
            this.name = name;

            return this;
        }

        public MenuCreateRequestBuilder menuProduct(final Product product, final Long quantity) {
            MenuProduct menuProduct = new MenuProduct();
            menuProduct.setProductId(product.getId());
            menuProduct.setQuantity(quantity);

            menuProducts.add(menuProduct);
            this.price = this.price.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));

            return this;
        }

        public MenuCreateRequestBuilder menuGroupId(final UUID menuGroupId) {
            this.menuGroupId = menuGroupId;

            return this;
        }

        public MenuCreateRequestBuilder addPrice(final long price) {
            this.price = this.price.add(BigDecimal.valueOf(price));

            return this;
        }

        public MenuCreateRequestBuilder isDisplay(final boolean isDisplay) {
            this.isDisplay = isDisplay;

            return this;
        }

        public Menu build() {
            Menu menu = new Menu();
            menu.setMenuGroupId(this.menuGroupId);
            menu.setMenuProducts(this.menuProducts);
            menu.setName(this.name);
            menu.setPrice(this.price);
            menu.setDisplayed(this.isDisplay);

            return menu;
        }
    }

    public static class MenuUpdateRequestBuilder {

        private BigDecimal price;

        public MenuUpdateRequestBuilder price(final long price) {
            this.price = BigDecimal.valueOf(price);

            return this;
        }

        public Menu build() {
            Menu menu = new Menu();
            menu.setPrice(this.price);

            return menu;
        }
    }
}
