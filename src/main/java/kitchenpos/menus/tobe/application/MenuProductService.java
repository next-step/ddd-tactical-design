package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.products.tobe.application.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MenuProductService {

    private final ProductService productService;

    public MenuProductService(ProductService productService) {
        this.productService = productService;
    }

    public BigDecimal getTotalMenuProductsPrice(final List<MenuProduct> menuProducts) {
        BigDecimal totalMenuProductPrice = menuProducts.stream()
                .map(p -> {
                    BigDecimal price = productService.getPriceByProductId(p.getProductId());
                    return price.multiply(BigDecimal.valueOf(p.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalMenuProductPrice;
    }


}
