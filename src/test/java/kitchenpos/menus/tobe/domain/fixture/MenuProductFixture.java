package kitchenpos.menus.tobe.domain.fixture;

import kitchenpos.common.domain.model.Price;
import kitchenpos.menus.tobe.domain.model.MenuQuantity;
import kitchenpos.menus.tobe.domain.model.MenuProduct;
import kitchenpos.products.tobe.domain.model.Product;

import java.math.BigDecimal;

public class MenuProductFixture {

    public static MenuProduct MENU_PRODUCT_FIXTURE(Product product, Long priceValue, Long quantityValue) {
        Price price = new Price(BigDecimal.valueOf(priceValue));
        MenuQuantity menuQuantity = new MenuQuantity(BigDecimal.valueOf(quantityValue));

        return new MenuProduct(product.getId(), price, menuQuantity);
    }

}
