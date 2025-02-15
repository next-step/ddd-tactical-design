package kitchenpos.products.application;

import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductName;
import kitchenpos.products.domain.ProductPrice;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.infra.CheckBadWordClient;
import kitchenpos.products.ui.request.ChangePriceRequest;
import kitchenpos.products.ui.request.CreateProductRequest;
import kitchenpos.products.ui.response.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CheckBadWordClient checkBadWordClient;
    private final MenuService menuService;

    public ProductService(
            final ProductRepository productRepository,
            final CheckBadWordClient checkBadWordClient,
            final MenuService menuService
    ) {
        this.productRepository = productRepository;
        this.checkBadWordClient = checkBadWordClient;
        this.menuService = menuService;
    }

    @Transactional
    public ProductResponse create(final CreateProductRequest request) {
        if (checkBadWordClient.containsProfanity(request.getName())) {
            throw new IllegalArgumentException(
                    String.format("""
                            Product name contains profanity (name: %s)
                            """, request.getName())
            );
        }
        final Product product = Product.Companion.create(request.getName(), request.getPrice());
        final Product savedProduct = productRepository.save(product);
        return ProductResponse.of(savedProduct);
    }

    @Transactional
    public ProductResponse changePrice(final UUID productId, final ChangePriceRequest request) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        final Product updatedProduct = productRepository.save(product.changePrice(request.getPrice()));
        menuService.updateMenuDisplay(updatedProduct);
        return ProductResponse.of(productRepository.save(updatedProduct));
    }


    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        final List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductResponse::of)
                .toList();
    }
}
