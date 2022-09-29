package kitchenpos.menus.tobe;

import static java.util.List.of;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.global.vo.Name;
import kitchenpos.global.vo.Price;
import kitchenpos.global.vo.Quantity;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.menus.tobe.domain.vo.Product;
import kitchenpos.menus.tobe.dto.ProductDTO;
import kitchenpos.products.application.FakePurgomalumClient;

public final class MenuFixtures {

    private MenuFixtures() {
    }

    public static Name name(String value) {
        return new Name(value, new FakePurgomalumClient());
    }

    public static Price price(long value) {
        return new Price(BigDecimal.valueOf(value));
    }

    public static Quantity quantity(long value) {
        return new Quantity(value);
    }

    public static ProductDTO productDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice());
    }

    public static ProductDTO productDTO(UUID id, String name, long price) {
        return new ProductDTO(
                id,
                name(name),
                price(price));
    }

    public static Menu menu(String name) {
        return menu(
                name,
                "두마리 치킨",
                15_000,
                false,
                menuProduct(1, 16_000)
        );
    }

    public static Menu menu(long price, MenuProduct... menuProduct) {
        return menu(
                "후라이드+후라이드",
                "두마리 치킨",
                price,
                false,
                menuProduct
        );
    }

    public static Menu menu(
            String name,
            String groupName,
            long price,
            boolean displayed,
            MenuProduct... menuProduct
    ) {
        MenuProducts menuProducts = menuProducts(menuProduct);
        return new Menu(
                name(name),
                menuGroup(groupName),
                menuProducts,
                price(price),
                displayed
        );
    }

    public static MenuGroup menuGroup(String name) {
        return new MenuGroup(name(name));
    }

    public static MenuProduct menuProduct(long quantity, long productPrice) {
        return new MenuProduct(
                product(UUID.randomUUID(), "양념치킨", productPrice),
                quantity(quantity)
        );
    }

    public static MenuProduct menuProduct(UUID productId, long quantity) {
        return new MenuProduct(
                product(productId),
                quantity(quantity));
    }

    private static Product product(UUID productId) {
        return new Product(productId);
    }

    private static Product product(UUID productId, String name, long price) {
        return new Product(productId, name(name), price(price));
    }

    public static MenuProduct menuProduct(
            UUID productId,
            long quantity,
            long productPrice
    ) {
        return new MenuProduct(
                product(productId, "양념치킨", productPrice),
                quantity(quantity)
        );
    }

    public static MenuProducts menuProducts(MenuProduct... menuProducts) {
        return new MenuProducts(of(menuProducts));
    }
}
