package kitchenpos.products.tobe.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.products.tobe.domain.ProductMenuRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.TobeProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service("tobeProductService")
public class ProductService {
    private final TobeProductRepository tobeProductRepository;
    private final ProductMenuRepository productMenuRepository;
    private final PurgomalumClient purgomalumClient;

    public ProductService(
        final TobeProductRepository tobeProductRepository,
        final ProductMenuRepository productMenuRepository,
        final PurgomalumClient purgomalumClient
    ) {
        this.tobeProductRepository = tobeProductRepository;
        this.productMenuRepository = productMenuRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Product create(final RequestProduct request) {
        final BigDecimal price = request.getPrice();
        final String name = request.getName();
        final Product product = new Product(name, price, purgomalumClient);

        return tobeProductRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final RequestProduct request) {
        final BigDecimal price = request.getPrice();
        final Product product = tobeProductRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        product.changePrice(price);
        final List<Menu> menus = productMenuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                sum = menuProduct.getProduct()
                    .getPrice()
                    .multiply(BigDecimal.valueOf(menuProduct.getQuantity()));
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return tobeProductRepository.findAll();
    }
}
