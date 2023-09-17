package kitchenpos.products.application;

import kitchenpos.menus.tobe.domain.NewMenu;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.products.application.dto.CreateProductRequest;
import kitchenpos.products.domain.MenuProductPriceHandler;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.common.domain.DisplayNameChecker;
import kitchenpos.products.domain.Product;
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
    private final DisplayNameChecker displayNameChecker;
    private final MenuProductPriceHandler menuProductPriceHandler;

    public ProductService(
            final ProductRepository productRepository,
            final MenuRepository menuRepository,
            final DisplayNameChecker displayNameChecker,
            final MenuProductPriceHandler menuProductPriceHandler
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.displayNameChecker = displayNameChecker;
        this.menuProductPriceHandler = menuProductPriceHandler;
    }

    @Transactional
    public Product create(final CreateProductRequest request) {
        Product product = Product.create(UUID.randomUUID(), request.getPrice(), request.getName(), displayNameChecker);
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID id, final Long price) {
        Product product = findProductById(id);
        product.changePrice(price);
        final List<NewMenu> newMenus = menuRepository.findAllByProductId(id);
        Map<UUID, Product> productMap = findProductInMenus(newMenus);
        menuProductPriceHandler.hideMenuDisplayMenuPriceGreaterThanSum(productMap, newMenus);
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    private Map<UUID, Product> findProductInMenus(List<NewMenu> newMenus) {
        return productRepository.findAllByIdIn(
                        newMenus.stream()
                                .flatMap(menu -> menu.getMenuProductIds().stream())
                                .collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toMap(Product::getId, p -> p));
    }

    private Product findProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_PRODUCT));
    }
}
