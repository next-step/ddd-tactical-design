package kitchenpos.products.application;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.products.model.ProductModel;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;
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
        final PurgomalumClient purgomalumClient
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Product create(final ProductModel request) {
        final BigDecimal price = request.price();
        final ProductPrice productPrice = new ProductPrice(price);
        final ProductName productName = new ProductName(request.name(), purgomalumClient);

        final Product product = new Product(productName, productPrice);
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final ProductModel request) {
        final ProductPrice price = new ProductPrice(request.price());
        final Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        product.changePrice(price);
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.menuProducts().menuProducts()) {
                sum = sum.add(
                    menuProduct.product()
                        .priceValue()
                        .multiply(BigDecimal.valueOf(menuProduct.quantity().quantity()))
                );
            }
            if (menu.priceValue().compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
