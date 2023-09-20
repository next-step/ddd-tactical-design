package kitchenpos;

import kitchenpos.eatinorders.domain.*;
import kitchenpos.menus.application.FakeMenuDisplayedNameProfanities;
import kitchenpos.menus.tobe.domain.menu.*;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupDisplayedName;
import kitchenpos.menus.tobe.intrastructure.ProductClientImpl;
import kitchenpos.products.application.FakeProductNameProfanities;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class Fixtures {
    public static final UUID INVALID_ID = new UUID(0L, 0L);

    public static Menu menu(ProductRepository productRepository) {
        final Product product = product();
        productRepository.save(product);
        return menu(19_000L, true, productRepository, menuProductMaterial(product.getId()));
    }

    public static Menu menu(final long price, final boolean displayed, ProductRepository productRepository) {
        final Product product = product();
        productRepository.save(product);
        MenuProductMaterial menuProductMaterial = menuProductMaterial(product.getId());
        return new Menu(
                UUID.randomUUID(),
                MenuDisplayedName.from("후라이드+후라이드", menuDisplayedNamePolicy()),
                MenuPrice.from(BigDecimal.valueOf(price)),
                menuGroup(),
                displayed,
                MenuProducts.from(Arrays.asList(menuProductMaterial), new ProductClientImpl(productRepository))
        );
    }

    public static Menu menu(final long price, ProductRepository productRepository, final MenuProductMaterial... menuProductMaterials) {
        return menu(price, false, productRepository, menuProductMaterials);
    }

    public static Menu menu(final long price, final boolean displayed, ProductRepository productRepository, final MenuProductMaterial... menuProductMaterials) {
        final Product product = product();
        productRepository.save(product);
        return new Menu(
                UUID.randomUUID(),
                MenuDisplayedName.from("후라이드+후라이드", menuDisplayedNamePolicy()),
                MenuPrice.from(BigDecimal.valueOf(price)),
                menuGroup(),
                displayed,
                MenuProducts.from(Arrays.asList(menuProductMaterials), new ProductClientImpl(productRepository))
        );
    }

    public static MenuGroup menuGroup() {
        return menuGroup("두마리메뉴");
    }

    public static MenuGroup menuGroup(final String name) {
        return new MenuGroup(UUID.randomUUID(), new MenuGroupDisplayedName(name));
    }

    public static MenuProductMaterial menuProductMaterial() {
        return menuProductMaterial(UUID.randomUUID(), 2L);
    }

    public static MenuProductMaterial menuProductMaterial(UUID productId) {
        return menuProductMaterial(productId, 2L);
    }

    public static MenuProductMaterial menuProductMaterial(UUID productId, long quantity) {
        return new MenuProductMaterial(productId, quantity);
    }

    public static MenuProduct menuProduct() {
        final Product product = product();
        final InMemoryProductRepository productRepository = new InMemoryProductRepository();
        productRepository.save(product);
        return MenuProduct.from(
                product.getId(),
                2L,
                new ProductClientImpl(productRepository)
        );
    }

    public static MenuProduct menuProduct(final Product product, final long quantity) {
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
        productRepository.save(product);
        return MenuProduct.from(
                product.getId(),
                quantity,
                new ProductClientImpl(productRepository)
        );
    }

    public static Order order(final OrderStatus status, final String deliveryAddress) {
        final Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setType(OrderType.DELIVERY);
        order.setStatus(status);
        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        order.setOrderLineItems(Arrays.asList(orderLineItem()));
        order.setDeliveryAddress(deliveryAddress);
        return order;
    }

    public static Order order(final OrderStatus status) {
        final Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setType(OrderType.TAKEOUT);
        order.setStatus(status);
        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        order.setOrderLineItems(Arrays.asList(orderLineItem()));
        return order;
    }

    public static Order order(final OrderStatus status, final OrderTable orderTable) {
        final Order order = new Order();
        order.setId(UUID.randomUUID());
        order.setType(OrderType.EAT_IN);
        order.setStatus(status);
        order.setOrderDateTime(LocalDateTime.of(2020, 1, 1, 12, 0));
        order.setOrderLineItems(Arrays.asList(orderLineItem()));
        order.setOrderTable(orderTable);
        return order;
    }

    public static OrderLineItem orderLineItem() {
        final OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setSeq(new Random().nextLong());
        orderLineItem.setMenu(menu(new InMemoryProductRepository()));
        return orderLineItem;
    }

    public static OrderTable orderTable() {
        return orderTable(false, 0);
    }

    public static OrderTable orderTable(final boolean occupied, final int numberOfGuests) {
        final OrderTable orderTable = new OrderTable();
        orderTable.setId(UUID.randomUUID());
        orderTable.setName("1번");
        orderTable.setNumberOfGuests(numberOfGuests);
        orderTable.setOccupied(occupied);
        return orderTable;
    }

    public static Product product() {
        return product("후라이드", 16_000L);
    }

    public static Product product(final String name, final long price) {
        return new Product(UUID.randomUUID(), ProductName.from(name, productNamePolicy()), ProductPrice.from(BigDecimal.valueOf(price)));
    }

    public static ProductNamePolicy productNamePolicy() {
        return new ProductNamePolicy(new FakeProductNameProfanities());
    }

    public static MenuDisplayedNamePolicy menuDisplayedNamePolicy() {
        return new MenuDisplayedNamePolicy(new FakeMenuDisplayedNameProfanities());
    }
}
