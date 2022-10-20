package kitchenpos.products.application;

import kitchenpos.common.vo.DisplayedName;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.common.vo.Price;
import kitchenpos.products.application.dto.ProductRequest;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.common.infra.Profanities;
import kitchenpos.products.tobe.domain.Product;
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
    private final Profanities purgomalumClient;

    public ProductService(
            final ProductRepository productRepository,
            final MenuRepository menuRepository,
            final Profanities purgomalumClient
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Product create(final ProductRequest request) {
        final Product product = new Product(
                new Price(request.getPrice()), new DisplayedName(request.getName(), purgomalumClient));
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final ProductRequest request) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.changePrice(new Price(request.getPrice()));
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
           Price sum = new Price(BigDecimal.ZERO);
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                sum.add(
                        menuProduct.getSumOfPrice()
                );
            }
            if (menu.getPrice().compareTo(sum) > 0) {
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
