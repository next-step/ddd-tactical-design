package kitchenpos.menu.tobe.domain.menu.validate;

import kitchenpos.menu.tobe.domain.menu.MenuProduct;
import kitchenpos.menu.tobe.domain.menu.ProductApiClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductValidator {
    private final ProductApiClient productApiClient;

    public ProductValidator(ProductApiClient productApiClient) {
        this.productApiClient = productApiClient;
    }

    public void validateProductExistence(List<MenuProduct> menuProducts) {
        long actualProductCount = productApiClient.countProductsByIds(
                menuProducts.stream()
                        .map(MenuProduct::getProductId)
                        .toList()
        );

        if (actualProductCount != menuProducts.size()) {
            throw new IllegalArgumentException("메뉴에 속한 상품의 갯수가 실제 상품과 일치하지 않습니다.");
        }
    }

}
