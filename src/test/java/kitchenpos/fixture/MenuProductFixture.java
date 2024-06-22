package kitchenpos.fixture;

import java.util.List;
import kitchenpos.menus.domain.tobe.MenuProduct;
import kitchenpos.menus.domain.tobe.ProductQuantity;
import kitchenpos.menus.application.dto.MenuProductCreateRequest;
import kitchenpos.menus.application.dto.MenuProductCreateRequests;
import kitchenpos.products.domain.tobe.Product;

public class MenuProductFixture {

    public static MenuProductCreateRequests createRequests(Product product, Integer quantity) {
        return new MenuProductCreateRequests(
                List.of(new MenuProductCreateRequest(product.getId(),
                        new ProductQuantity(quantity))));
    }

    public static MenuProduct createFired(long quantity) {
        return new MenuProduct(ProductFixture.createFired(), new ProductQuantity(quantity));
    }

    public static MenuProduct createSeasoned(long quantity) {
        return new MenuProduct(ProductFixture.createSeasoned(), new ProductQuantity(quantity));
    }
}
