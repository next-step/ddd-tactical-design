package kitchenpos.fixture;

import java.util.List;
import kitchenpos.menus.domain.tobe.MenuProduct;
import kitchenpos.menus.domain.tobe.MenuQuantity;
import kitchenpos.menus.ui.dto.MenuProductCreateRequest;
import kitchenpos.menus.ui.dto.MenuProductCreateRequests;
import kitchenpos.products.domain.tobe.Product;

public class MenuProductFixture {

    public static MenuProductCreateRequests createRequests(Product product, Integer quantity) {
        return new MenuProductCreateRequests(
                List.of(new MenuProductCreateRequest(product.getId(), new MenuQuantity(quantity))));
    }

    public static MenuProduct createFired(long quantity) {
        return new MenuProduct(ProductFixture.createFired(), new MenuQuantity(quantity));
    }

    public static MenuProduct createSeasoned(long quantity) {
        return new MenuProduct(ProductFixture.createSeasoned(), new MenuQuantity(quantity));
    }
}
