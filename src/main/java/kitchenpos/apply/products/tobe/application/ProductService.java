package kitchenpos.apply.products.tobe.application;

import kitchenpos.apply.menus.tobe.domain.MenuPriceCalculator;
import kitchenpos.apply.menus.tobe.domain.MenuRepository;
import kitchenpos.apply.products.tobe.ui.ProductRequest;
import kitchenpos.apply.products.tobe.ui.ProductResponse;
import kitchenpos.apply.products.tobe.domain.Product;
import kitchenpos.apply.products.tobe.domain.ProductName;
import kitchenpos.apply.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductNameFactory productNameFactory;
    private final MenuRepository menuRepository;
    private final MenuPriceCalculator menuPriceCalculator;

    public ProductService(
        final ProductRepository productRepository,
        final ProductNameFactory productNameFactory,
        final MenuRepository menuRepository,
        final MenuPriceCalculator menuPriceCalculator
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.productNameFactory = productNameFactory;
        this.menuPriceCalculator = menuPriceCalculator;
    }

    @Transactional
    public ProductResponse create(final ProductRequest request) {
        final ProductName productName = productNameFactory.createProductName(request.getName());
        final Product savedProduct = productRepository.save(Product.of(productName, request.getPrice()));
        return new ProductResponse(savedProduct);
    }

    @Transactional
    public ProductResponse changePrice(final UUID productId, final ProductRequest request) {
        final Product product = productRepository.findById(productId).orElseThrow(NoSuchElementException::new);
        product.changePrice(request.getPrice());
        menuRepository.findAllByProductId(productId)
                .forEach(it -> it.hideIfMenuPriceTooHigher(menuPriceCalculator.getTotalPriceFrom(it)));
        return new ProductResponse(product);
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }
}
