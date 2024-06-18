package kitchenpos.products.tobe.application;


import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.products.tobe.*;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class ProductService {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final ProductValidator productValidator;

    public ProductService(
            final ProductRepository productRepository,
            final MenuRepository menuRepository,
            final ProductValidator productValidator
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.productValidator = productValidator;
    }

    @Transactional
    public Product create(final CreateCommand createCommand) {
        final BigDecimal price = createCommand.price();
        final String name = createCommand.name();
        return productRepository.save(new Product(UUID.randomUUID(), name, price, productValidator));
    }

    @Transactional
    public Product changePrice(final UUID productId, final UpdateCommand updateCommand) {

        final BigDecimal price = updateCommand.price();
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.changePrice(price, productValidator);

        // TODO: 메뉴 BC 리팩토링때 반영
        /*
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
        */
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
