package kitchenpos.products.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
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
    public Product create(final Product request) {
        final BigDecimal price = request.getPrice();
        final String name = request.getDisplayedName();
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
        final Product product = new Product(UUID.randomUUID(), name, price);
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final Product request) {
        final BigDecimal price = request.getPrice();

        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        Product changePrice = product.changePrice(price);
        productRepository.save(product.changePrice(price));

        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                sum = sum.add(
                        productRepository.findById(menuProduct.getProduct().getId())
                                .orElseThrow(NoSuchElementException::new)
                                .getPrice()
                                .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                );
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }
        return changePrice;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
