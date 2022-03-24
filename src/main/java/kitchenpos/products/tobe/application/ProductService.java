package kitchenpos.products.tobe.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.domain.DefaultProfanities;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.domain.Profanities;
import kitchenpos.products.tobe.ui.dto.ProductChangePriceRequest;
import kitchenpos.products.tobe.ui.dto.ProductCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final Profanities profanities;

    @Autowired
    public ProductService(final ProductRepository productRepository, final MenuRepository menuRepository) {
        this(productRepository, menuRepository, new DefaultProfanities());
    }

    public ProductService(final ProductRepository productRepository, final MenuRepository menuRepository, final Profanities profanities) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.profanities = profanities;
    }

    @Transactional
    public Product create(final ProductCreateRequest request) {
        final Product product = request.toEntity(profanities);

        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(UUID productId, ProductChangePriceRequest request) {
        final Product product = findById(productId);
        product.setPrice(request.price());

        final List<Menu> menus = menuRepository.findAllByProductId(productId);
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
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product findById(final UUID productId) {
        return productRepository.findById(productId).orElseThrow(NoSuchElementException::new);
    }
}
