package kitchenpos.products.application;

import kitchenpos.menus.application.MenuService;
import kitchenpos.products.dto.ProductChangePriceRequest;
import kitchenpos.products.dto.ProductCreateRequest;
import kitchenpos.products.dto.ProductResponse;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.domain.ProfanityChecker;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProfanityChecker profanityChecker;
    private final MenuService menuService;

    public ProductService(
        final ProductRepository productRepository,
        final MenuService menuService,
        final ProfanityChecker profanityChecker
    ) {
        this.productRepository = productRepository;
        this.menuService = menuService;
        this.profanityChecker = profanityChecker;
    }

    @Transactional
    public ProductResponse create(final ProductCreateRequest request) {
        final ProductPrice price = ProductPrice.from(request.price());
        final ProductName name = ProductName.from(request.name(), profanityChecker);
        final Product product = productRepository.save(Product.from(name, price));
        return ProductResponse.of(product);
    }

    @Transactional
    public ProductResponse changePrice(final UUID productId, final ProductChangePriceRequest request) {
        final ProductPrice price = ProductPrice.from(request.price());
        final Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        product.changePrice(price);
        menuService.checkHideMenuBasedOnProductPrice(productId);
        return ProductResponse.of(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        final List<Product> productList = productRepository.findAll();
        return productList.stream()
                .map(ProductResponse::of)
                .toList();
    }
}
