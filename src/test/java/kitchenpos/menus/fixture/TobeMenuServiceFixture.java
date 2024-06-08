package kitchenpos.menus.fixture;

import kitchenpos.menus.tobe.domain.DisplayName;
import kitchenpos.menus.tobe.domain.Displayed;
import kitchenpos.menus.tobe.domain.MenuPrice;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.menus.tobe.domain.TobeMenu;
import kitchenpos.menus.tobe.domain.TobeMenuGroup;
import kitchenpos.menus.tobe.domain.TobeMenuProduct;
import kitchenpos.menus.tobe.dto.request.MenuCreateRequest;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;

import java.math.BigDecimal;
import java.util.UUID;

public class TobeMenuServiceFixture {

    public static TobeMenuGroup createMenuGroup() {
        return TobeMenuGroup.create("인기 메뉴");
    }

    public static Product createProduct(Long price, PurgomalumClient purgomalumClient) {
        ProductName productName = ProductName.of("상품명", purgomalumClient);
        ProductPrice productPrice = ProductPrice.of(BigDecimal.valueOf(price));
        return Product.create(productName, productPrice);
    }

    public static Product createProduct(PurgomalumClient purgomalumClient) {
        ProductName productName = ProductName.of("상품명" + UUID.randomUUID(), purgomalumClient);
        ProductPrice productPrice = ProductPrice.of(BigDecimal.valueOf(10000));
        return Product.create(productName, productPrice);
    }

    public static TobeMenuProduct createMenuProduct(Product product, Long quantity) {
        return TobeMenuProduct.create(product.getId(), product.getPrice(), quantity);
    }

    public static MenuCreateRequest.MenuProductRequest createMenuProductRequest(Product product, Long quantity) {
        return new MenuCreateRequest.MenuProductRequest(product.getId(), quantity);
    }

    public static TobeMenu createMenu(String name, BigDecimal bigDecimal, PurgomalumClient purgomalumClient, MenuProducts menuProducts) {
        return TobeMenu.create(
                DisplayName.of(name, purgomalumClient),
                MenuPrice.of(bigDecimal),
                createMenuGroup().getId(),
                Displayed.DISPLAYED,
                menuProducts
        );
    }
}
