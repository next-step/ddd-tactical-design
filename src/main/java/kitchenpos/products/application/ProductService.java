package kitchenpos.products.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.vo.Products;
import kitchenpos.products.infra.PurgomalumClient;
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
    private final PurgomalumClient purgomalumClient;

    public ProductService(
            final ProductRepository productRepository,
            final MenuRepository menuRepository,
            PurgomalumClient purgomalumClient) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Product create(final Product request) {
        final BigDecimal price = request.getPrice();
        final String name = request.getDisplayedName();
        final Product product = new Product(UUID.randomUUID(), name, price, purgomalumClient);
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final Product request) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);

        Product changePrice = product.changePrice(request.getPrice());
        productRepository.save(changePrice);

        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                sum = sum.add(
                        menuProduct.getProduct()
                                .getPrice()
                                .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                );
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.displayed();
            }
        }
        return changePrice;

    }

    @Transactional(readOnly = true)
    public Products findAllByIdIn(List<UUID> productIds) {
        return new Products(productRepository.findAllByIdIn(productIds));
    }

    @Transactional(readOnly = true)
    public Product findById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);

    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }


}
