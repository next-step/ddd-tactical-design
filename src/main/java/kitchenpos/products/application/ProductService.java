package kitchenpos.products.application;

import kitchenpos.menus.domain.Menu;
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
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final CheckBadWordClient checkBadWordClient;

    public ProductService(
            final ProductRepository productRepository,
            final MenuRepository menuRepository,
            final CheckBadWordClient checkBadWordClient
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.checkBadWordClient = checkBadWordClient;
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
        final Product oldProduct = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));

        // Create new Product instance with updated price
        final Product updatedProduct = new Product(
                oldProduct.getId(),
                ProductName.Companion.create(oldProduct.getName()),
                ProductPrice.Companion.create(request.getPrice())
        );

        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
            validateAndUpdateMenuDisplay(menu);
        }

        final Product product = productRepository.save(updatedProduct);
        return ProductResponse.of(product);
    }

    private void validateAndUpdateMenuDisplay(Menu menu) {
        BigDecimal sum = calculateMenuTotalPrice(menu);
        if (menu.getPrice().compareTo(sum) > 0) {
            menu.setDisplayed(false);
        }
    }

    private BigDecimal calculateMenuTotalPrice(Menu menu) {
        return menu.getMenuProducts().stream()
                .map(menuProduct -> menuProduct.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(menuProduct.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
