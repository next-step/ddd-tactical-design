package kitchenpos.products.tobe.application;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.ui.ProductRequest;
import kitchenpos.products.tobe.ui.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final ProductNameFactory productNameFactory;

    public ProductService(
        final ProductRepository productRepository,
        final MenuRepository menuRepository,
        final ProductNameFactory productNameFactory
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.productNameFactory = productNameFactory;
    }

    @Transactional
    public ProductResponse create(final ProductRequest request) {
        ProductName productName = productNameFactory.createProductName(request.getName());
        Product savedProduct = productRepository.save(Product.of(productName, request.getPrice()));
        return new ProductResponse(savedProduct);
    }

    @Transactional
    public ProductResponse changePrice(final UUID productId, final ProductRequest request) {
        final Product product = productRepository.findById(productId).orElseThrow(NoSuchElementException::new);

        product.changePrice(request.getPrice());

        menuRepository.findAllByProductId(productId)
                .forEach(Menu::hideIfMenuPriceTooHigher);

        return new ProductResponse(product);
    }



    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(ProductResponse::new)
                .collect(Collectors.toList());
    }
}
