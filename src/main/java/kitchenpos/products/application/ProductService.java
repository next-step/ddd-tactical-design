package kitchenpos.products.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.domain.ChangedPriceApplier;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import static kitchenpos.products.exception.ProductExceptionMessage.NOT_FOUND_PRODUCT;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final PurgomalumClient purgomalumClient;

    public ProductService(
            final ProductRepository productRepository,
            final MenuRepository menuRepository,
            final PurgomalumClient purgomalumClient
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Product create(final CreateProductRequest request) {
        Product product = Product.create(request.getPrice(), request.getName(), purgomalumClient);
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final ChangeProductPriceRequest request) {
        final Product product = findProductById(request);
        final List<Menu> menus = menuRepository.findAllByProductId(request.getId());
        Map<UUID, Product> productMap = findProductInMenus(menus);
        ChangedPriceApplier.hideMenuDisplayMenuPriceGreaterThanSum(productMap, menus);
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    private Map<UUID, Product> findProductInMenus(List<Menu> menus) {
        return productRepository.findAllByIdIn(
                        menus.stream()
                                .flatMap(menu -> menu.getMenuProductIds().stream())
                                .collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toMap(Product::getId, p -> p));
    }

    private Product findProductById(ChangeProductPriceRequest request) {
        final Product product = productRepository.findById(request.getId())
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_PRODUCT));
        product.changePrice(request.getPrice());
        return product;
    }
}
