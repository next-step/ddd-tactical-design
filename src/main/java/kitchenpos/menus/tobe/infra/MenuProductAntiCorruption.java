package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class MenuProductAntiCorruption {

    private ProductRepository productRepository;

    public MenuProductAntiCorruption(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public BigDecimal menuTotalPrice(List<MenuProduct> menuProducts) {
        for (MenuProduct menuProduct : menuProducts) {
            menuProduct.calculatePrice(getPriceByProduct(menuProduct.getProductId()));
        }

        return menuProducts.stream()
                .map(MenuProduct::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getPriceByProduct(Long productId) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품은 존재하지 않습니다."));
        return product.getPrice();
    }
}
