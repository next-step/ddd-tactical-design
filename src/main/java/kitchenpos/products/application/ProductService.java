package kitchenpos.products.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.ProfanityValidator;
import kitchenpos.products.domain.tobe.Price;
import kitchenpos.products.domain.tobe.Product;
import kitchenpos.products.ui.dto.ProductCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final ProfanityValidator profanityValidator;

    public ProductService(
            final ProductRepository productRepository,
            final MenuRepository menuRepository,
            final ProfanityValidator profanityValidator
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.profanityValidator = profanityValidator;
    }

    @Transactional
    public Product create(final ProductCreateRequest request) {
        request.validateName(profanityValidator);
        return productRepository.save(request.to());
    }

    @Transactional
    public Product changePrice(final UUID productId, final Price request) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.changePrice(request);
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