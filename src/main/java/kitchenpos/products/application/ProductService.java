package kitchenpos.products.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.dto.ProductChangePriceRequest;
import kitchenpos.products.dto.ProductCreateRequest;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProfanityChecker;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.ProductRepository;
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
    private final ProfanityChecker profanityChecker;

    public ProductService(
        final ProductRepository productRepository,
        final MenuRepository menuRepository,
        final ProfanityChecker profanityChecker
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.profanityChecker = profanityChecker;
    }

    @Transactional
    public Product create(final ProductCreateRequest request) {
        final ProductPrice price = ProductPrice.from(request.price());
        final ProductName name = ProductName.from(request.name(), profanityChecker);
        return productRepository.save(Product.from(name, price));
    }

    @Transactional
    public Product changePrice(final UUID productId, final ProductChangePriceRequest request) {
        final ProductPrice price = ProductPrice.from(request.price());
        final Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        product.changePrice(price);
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
