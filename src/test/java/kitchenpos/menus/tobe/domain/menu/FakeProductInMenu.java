package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.products.infra.FakePurgomalumClient;
import kitchenpos.support.product.vo.ProductName;
import kitchenpos.support.product.vo.ProductPrice;

import java.math.BigDecimal;
import java.util.UUID;

public class FakeProductInMenu {

    public static ProductInMenu createFake(UUID productId, String name, BigDecimal price) {
        return new ProductInMenu(
                productId,
                ProductName.create(name, new FakePurgomalumClient()),
                ProductPrice.create(price)
        );
    }

}
