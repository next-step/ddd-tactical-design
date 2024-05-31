package kitchenpos.product.tobe.application;

import kitchenpos.common.infra.PurgomalumClient;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.product.tobe.application.dto.ChangePriceRequest;
import kitchenpos.product.tobe.application.dto.CreateProductRequest;
import kitchenpos.product.tobe.domain.Product;
import kitchenpos.product.tobe.domain.ProductRepository;
import kitchenpos.product.tobe.domain.validate.ProfanityValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service("newProductService")
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
        final Product product = new Product(UUID.randomUUID(), request.name(), request.price(), new ProfanityValidator(purgomalumClient));
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final ChangePriceRequest request) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.changePrice(request.price());

        updateMenusDisplayStatus(productId);

        return product;
    }

    private void updateMenusDisplayStatus(UUID productId) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
            updateMenuDisplayStatus(menu);
        }
    }

    private void updateMenuDisplayStatus(Menu menu) {
        BigDecimal totalMenuPrice = menu.getMenuProducts().stream()
                .map(menuProduct -> menuProduct.getProduct().getPrice().multiply(BigDecimal.valueOf(menuProduct.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (menu.getPrice().compareTo(totalMenuPrice) > 0) {
            menu.setDisplayed(false);
        }
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
