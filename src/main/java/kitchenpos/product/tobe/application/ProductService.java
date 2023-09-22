package kitchenpos.product.tobe.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menu.tobe.domain.Menu;
import kitchenpos.menu.tobe.domain.MenuRepository;
import kitchenpos.product.tobe.application.dto.ChangeProductPriceRequest;
import kitchenpos.product.tobe.application.dto.ChangeProductPriceResponse;
import kitchenpos.product.tobe.application.dto.CreateProductRequest;
import kitchenpos.product.tobe.application.dto.CreateProductResponse;
import kitchenpos.product.tobe.domain.Product;
import kitchenpos.product.tobe.domain.ProductName;
import kitchenpos.product.tobe.domain.ProductRepository;
import kitchenpos.product.tobe.domain.service.ProductNamePolicy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final ProductNamePolicy productNamePolicy;

    public ProductService(ProductRepository productRepository, MenuRepository menuRepository, ProductNamePolicy productNamePolicy) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.productNamePolicy = productNamePolicy;
    }

    @Transactional
    public CreateProductResponse create(final CreateProductRequest request) {
        final Product product = new Product(UUID.randomUUID(), ProductName.of(request.getName(), productNamePolicy), request.getPrice());
        return CreateProductResponse.of(productRepository.save(product));
    }

    @Transactional
    public ChangeProductPriceResponse changePrice(final UUID productId, final ChangeProductPriceRequest request) {
        final Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        product.changePrice(request.getPrice());

        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        menus.forEach(Menu::checkPrice);

        return ChangeProductPriceResponse.of(product);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
