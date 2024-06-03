package kitchenpos.products.application.tobe;

import jakarta.transaction.Transactional;
import kitchenpos.menus.application.tobe.MenuService;
import kitchenpos.products.domain.tobe.ProductRepository;
import kitchenpos.products.domain.tobe.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

//@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MenuService menuService;

    public ProductService(ProductRepository productRepository, MenuService menuService) {
        this.productRepository = productRepository;
        this.menuService = menuService;
    }

    @Transactional
    public Product changePrice(final UUID productId, final BigDecimal price) throws Exception {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("product can't find by id"));

        product.changePrice(price);

        menuService.changePriceOfProduct(productId, price);

        return product;
    }
}
