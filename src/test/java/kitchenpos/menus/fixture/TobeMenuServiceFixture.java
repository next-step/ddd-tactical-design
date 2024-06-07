package kitchenpos.menus.fixture;

import kitchenpos.menus.tobe.domain.TobeMenuGroup;
import kitchenpos.menus.tobe.dto.request.MenuCreateRequest;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;

import java.math.BigDecimal;

public class TobeMenuServiceFixture {

    /* TobeMenuGroup menuGroup = TobeMenuGroup.create("인기 메뉴");        Product.create(ProductName.of("상품명",purgomalumClient), ProductPrice.of(BigDecimal.valueOf(10000)));


        List<MenuCreateRequest.MenuProductRequest> menuProductRequests = List.of(new MenuCreateRequest.MenuProductRequest(UUID.randomUUID(), 1L),
                new MenuCreateRequest.MenuProductRequest(UUID.randomUUID(), 1L));
*/

    public static TobeMenuGroup createMenuGroup() {
        return TobeMenuGroup.create("인기 메뉴");
    }

    public static TobeMenuGroup createMenuGroup(String name) {
        return TobeMenuGroup.create(name);
    }

    public static Product createProduct(String name, Long price, PurgomalumClient purgomalumClient) {
        ProductName productName = ProductName.of(name, purgomalumClient);
        ProductPrice productPrice = ProductPrice.of(BigDecimal.valueOf(price));
        return Product.create(productName, productPrice);
    }

    public static Product createProduct(Long price, PurgomalumClient purgomalumClient) {
        ProductName productName = ProductName.of("상품명", purgomalumClient);
        ProductPrice productPrice = ProductPrice.of(BigDecimal.valueOf(price));
        return Product.create(productName, productPrice);
    }

    public static Product createProduct(PurgomalumClient purgomalumClient) {
        ProductName productName = ProductName.of("상품명", purgomalumClient);
        ProductPrice productPrice = ProductPrice.of(BigDecimal.valueOf(10000));
        return Product.create(productName, productPrice);
    }

    public static MenuCreateRequest.MenuProductRequest createMenuProductRequest(Product product, Long quantity) {
        return new MenuCreateRequest.MenuProductRequest(product.getId(), quantity);
    }

}
