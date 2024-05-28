package kitchenpos.product.application;

import kitchenpos.infra.PurgomalumClient;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuProduct;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.product.tobe.domain.Product;
import kitchenpos.product.tobe.domain.ProductName;
import kitchenpos.product.tobe.domain.ProductPrice;
import kitchenpos.product.tobe.domain.ProductRepository;
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
        final String name = request.getProductName();
        final BigDecimal price = request.getProductPrice();
        final var productName = new ProductName(name, checkContainsProfanity(name));
        final var productPrice = new ProductPrice(price);

        final Product product = new Product(productName, productPrice);
        return productRepository.save(product);
    }

    boolean checkContainsProfanity(String name) {
        return purgomalumClient.containsProfanity(name);
    }

    @Transactional
    public Product changePrice(final UUID productId, final Product request) {
        final BigDecimal price = request.getProductPrice();
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);

        final var productPrice = new ProductPrice(price);
        product.setProductPrice(productPrice);

        changeMenuDisplayStatus(productId);

        return product;
    }

    private void changeMenuDisplayStatus(UUID productId) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                sum = sum.add(
                        menuProduct.getProduct()
                                .getProductPrice()
                                .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                );
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
